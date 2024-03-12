use std::{thread};
use std::net::{TcpListener, TcpStream, Shutdown};
use std::io::{Read, Write};
use std::sync::{Arc, Mutex};

fn handle_client(mut stream: TcpStream, dim: Arc<Mutex<i32>>) {
    let mut data = [0 as u8; 50]; // using 50 byte buffer
    while match stream.read(&mut data) {
        Ok(size) => {
            let msg = std::str::from_utf8(&data[0..size]).expect("Conversion error");
            println!("{}", msg);
            let dim_str = dim.lock().unwrap().to_string();
            let out = format!("{}\n", dim_str); // add an end string character
            // echo everything!
            stream.write(out.as_bytes()).unwrap();
            true
        },
        Err(_) => {
            println!("An error occurred, terminating connection with {}", stream.peer_addr().unwrap());
            stream.shutdown(Shutdown::Both).unwrap();
            false
        }
    } {}
}

fn main() {
    thread::spawn(move || {});
    let listener = TcpListener::bind("0.0.0.0:3333").unwrap();
    // accept connections and process them, spawning a new thread for each one
    println!("Server listening on port 3333");
    let dim = Arc::new(Mutex::new(0));
    for stream in listener.incoming() {
        match stream {
            Ok(stream) => {
                println!("New connection: {}", stream.peer_addr().unwrap());
                let dim = Arc::clone(&dim);
                thread::spawn(move || {
                    // connection succeeded
                    handle_client(stream, dim)
                });
            }
            Err(e) => {
                println!("Error: {}", e);
                /* connection failed */
            }
        }
    }
    // close the socket server
    drop(listener);
}
