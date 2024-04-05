use std::io::Read;
use std::net::TcpStream;

pub fn read_utf(stream: &mut TcpStream) -> String{
    let mut buffer = [0; 1024]; // Clear buffer
    let bytes_read = stream.read(&mut buffer).expect("Failed to read from socket");
    let out = String::from_utf8_lossy(&buffer[..bytes_read]).to_string();
    println!("Recieved message: {}", out);
    out
}
pub fn read_int(stream: &mut TcpStream) -> i32 {
    let mut buffer = [0; 1024]; // Clear buffer
    let bytes_read = stream.read(&mut buffer).expect("Failed to read from socket") as i32;
    bytes_read
}
