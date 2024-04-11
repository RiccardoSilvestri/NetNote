use std::net::TcpListener;
use std::sync::{Arc, Mutex};
use std::{env, thread};
mod connection;
use connection::handle_client::*;

fn main() {
    // Initialize the file access lock
    let file_access = Arc::new(Mutex::new(()));
    let args: Vec<String> = env::args().collect();
    let mut hostname = "0.0.0.0".to_owned();
    let mut port = 4444;

    match args.len() {
        2 => {
            if let Ok(p) = args[1].parse::<u16>() {
                port = p;
            } else {
                hostname = args[1].clone();
            }
        }
        3 => {
            hostname = args[1].clone();
            port = args[2].parse::<u16>().unwrap_or(4444);
        }
        _ => {}
    }

    let mut addr = hostname + ":";
    addr.push_str(&*port.to_string());
    let listener = TcpListener::bind(&addr);
    match listener {
        Ok(listener) => {
            println!("Server started on {}", addr);
            for stream in listener.incoming() {
                match stream {
                    Ok(stream) => {
                        // Clone the Arc<Mutex<()>> before moving it into the closure
                        let file_access_clone = Arc::clone(&file_access);
                        let _ = thread::spawn(move || {
                            let result = std::panic::catch_unwind(|| {
                                handle_client(stream, file_access_clone);
                            });
                            if let Err(_panic) = result {
                                eprintln!("A client connection caused a thread to panic.");
                            }
                        });
                    }
                    Err(e) => { eprintln!("Unable to connect: {}", e); }
                }
            }
        },
        Err(e) => {
            eprintln!("Could not bind to {}: {}", addr, e);
        }
    }
}
