use std::io::{Read, Write};
use std::net::{TcpListener, TcpStream};
use std::thread;
use serde_json::Value;

fn handle_client(mut stream: TcpStream) {
    let mut buffer = [0; 512];
    loop {
        let bytes_read = stream.read(&mut buffer).expect("Failed to read from socket");
        if bytes_read == 0 { return; } // connection closed

        let request = String::from_utf8_lossy(&buffer[..bytes_read]);
        let json: Value = serde_json::from_str(&request).expect("Failed to parse JSON");
        let response = serde_json::to_string(&json).expect("Failed to serialize JSON");
        stream.write(response.as_bytes()).expect("Failed to write to socket");
        stream.flush().expect("Failed to flush to socket");
    }
}

fn main() {
    let listener = TcpListener::bind("127.0.0.1:3333").expect("Could not bind");
    for stream in listener.incoming() {
        match stream {
            Ok(stream) => {
                thread::spawn(|| { handle_client(stream); });
            }
            Err(e) => { eprintln!("Unable to connect: {}", e); }
        }
    }
}
