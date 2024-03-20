use std::net::TcpListener;
use std::thread;
mod connection;
use connection::handle_client::*;

fn main() {
    let port = 4444;
    let mut addr = "0.0.0.0:".to_owned();
    addr.push_str(&*port.to_string());
    let listener = TcpListener::bind(addr).expect("Could not bind");
    println!("Server started on port {}", port);
    for stream in listener.incoming() {
        match stream {
            Ok(stream) => {
                println!("{:?}", stream);
                let handle = thread::spawn(|| {
                    let result = std::panic::catch_unwind(|| {
                        handle_client(stream);
                    });
                    if let Err(_panic) = result {
                        eprintln!("A client connection caused a thread to panic.");
                    }
                });
                // Wait for the thread to finish.
                handle.join().unwrap();
            }
            Err(e) => { eprintln!("Unable to connect: {}", e); }
        }
    }
}