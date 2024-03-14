use std::io::{BufWriter, Write};
use std::net::TcpStream;

pub fn send_utf(msg:String, stream: TcpStream){
    let mut writer = BufWriter::new(&stream);
    let length = msg.len() as u16;
    writer.write(&length.to_be_bytes()).expect("Failed to write length to socket");
    writer.write(msg.as_bytes()).expect("Failed to write to socket");
    writer.flush().expect("Failed to flush to socket");
}