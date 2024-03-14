use std::io::{BufWriter, Read, Write};
use std::net::TcpStream;
use super::handle_json::*;

pub(crate) fn handle_client(mut stream: TcpStream) {
    let mut buffer = [0; 512]; //
    loop {
        let bytes_read = stream.read(&mut buffer).expect("Failed to read from socket");
        if bytes_read == 0 { return; } // connection closed

        let request = String::from_utf8_lossy(&buffer[..bytes_read]);
        println!("Received: {}", request);

        // TODO: handle the message to use different functions
        // Convert received string to json
        let response = handle_json(request);

        let mut writer = BufWriter::new(&stream);

        // The java client needs to read the length of the string first, then the string. (readUTF)
        let length = response.len() as u16;
        writer.write(&length.to_be_bytes()).expect("Failed to write length to socket");
        writer.write(response.as_bytes()).expect("Failed to write to socket");
        writer.flush().expect("Failed to flush to socket");
    }
}