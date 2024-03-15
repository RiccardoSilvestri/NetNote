use std::io::Read;
use std::net::TcpStream;

pub fn read_stream(stream: &mut TcpStream) -> String{
    let mut buffer = [0; 1024]; // Clear buffer
    let bytes_read = stream.read(&mut buffer).expect("Failed to read from socket");
    String::from_utf8_lossy(&buffer[..bytes_read]).to_string()
}