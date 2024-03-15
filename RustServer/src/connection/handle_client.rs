use std::io::{Read};
use std::net::TcpStream;
use crate::connection::handle_json::handle_json;
use super::send_utf::*;

fn read_stream(stream: &mut TcpStream) -> String{
    let mut buffer = [0; 1024]; // Clear buffer
    let bytes_read = stream.read(&mut buffer).expect("Failed to read from socket");
    String::from_utf8_lossy(&buffer[..bytes_read]).to_string()
}

pub(crate) fn handle_client(mut stream: TcpStream) {
    let mut logged = false;
    loop {
        while !logged{
            let request = read_stream(&mut stream);
            if request.is_empty(){ return };
            println!("Received: {}", request);
            if request.eq_ignore_ascii_case("1") {
                println!("registered, now send json");
                send_utf("registered".to_string(), stream.try_clone().unwrap());
                logged = true;
                // TODO: register function
            } else if request.eq_ignore_ascii_case("2") {
                // TODO: login function
            } else { println!("Error!") }
        }
        let request = read_stream(&mut stream);
        if request.is_empty(){ return };
        println!("Received: {}", request);

        // TODO: handle the message to use different functions
        // Convert received string to json
        let response = handle_json(request, "received.json");

        // The java client needs to read the length of the string first, then the string. (readUTF)
        send_utf(response, stream.try_clone().unwrap());
    }
}
