use std::io::{BufWriter, Read, Write};
use std::net::TcpStream;
use crate::connection::handle_json::handle_json;
use super::send_utf::*;

pub(crate) fn handle_client(mut stream: TcpStream) {
    loop {
        let logged = false;
        while (!logged){
            let mut buffer = [0; 1024];
            let mut bytes_read = stream.read(&mut buffer).expect("Failed to read from socket");
            if bytes_read == 0 { return; } // connection closed
            let mut request = String::from_utf8_lossy(&buffer[..bytes_read]).to_string();
            println!("Received: {}", request);
            if request.eq_ignore_ascii_case("1") {
                println!("registered, now send json");
                send_utf("registered".to_string(), stream.try_clone().unwrap());
                // TODO: register function
            } else if request.eq_ignore_ascii_case("2") {
                // TODO: login function
            } else { println!("Error!") }
        }
        let mut buffer = [0; 1024]; // Clear the buffer
        let bytes_read  = stream.read(&mut buffer).expect("Failed to read from socket");
        if bytes_read == 0 { return; } // connection closed
        let request = String::from_utf8_lossy(&buffer[..bytes_read]).to_string();
        println!("Received: {}", request);

        // TODO: handle the message to use different functions
        // Convert received string to json
        let response = handle_json(request);

        // The java client needs to read the length of the string first, then the string. (readUTF)
        send_utf(response, stream.try_clone().unwrap());
    }
}
