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
                thread::spawn(|| { handle_client(stream); });
            }
            Err(e) => { eprintln!("Unable to connect: {}", e); }
        }
    }
}