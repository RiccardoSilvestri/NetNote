use std::io::{Read, Write, BufWriter};
use std::net::{TcpListener, TcpStream};
use std::thread;
fn handle_client(mut stream: TcpStream) {
    let mut buffer = [0; 512];
    loop {
        let bytes_read = stream.read(&mut buffer).expect("Failed to read from socket");
        if bytes_read == 0 { return; } // connection closed

        let request = String::from_utf8_lossy(&buffer[..bytes_read]);
        println!("Recieved: {request}");

        let mut response = request.trim();
        if request.trim().eq_ignore_ascii_case("pizza"){
            response = "margherita";
        }

        let mut writer = BufWriter::new(&stream);
        let length = response.len() as u16;
        // The java client needs to read the length of the string first, then the string. (readUTF)
        writer.write(&length.to_be_bytes()).expect("Failed to write length to socket");
        writer.write(response.as_bytes()).expect("Failed to write to socket");
        writer.flush().expect("Failed to flush to socket");
    }
}

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
