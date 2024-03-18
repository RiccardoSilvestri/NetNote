use std::net::TcpListener;
use std::thread;
mod connection;
use connection::handle_client::*;

fn main() {
    let port = 3333;
    let mut addr = "127.0.0.1:".to_owned();
    addr.push_str(&*port.to_string());
    let listener = TcpListener::bind(addr).expect("Could not bind");
    println!("Server started on port {}", port);
    for stream in listener.incoming() {
        match stream {
            Ok(stream) => {
                thread::spawn(|| { handle_client(stream); });
            }
            Err(e) => { eprintln!("Unable to connect: {}", e); }
        }
    }
}