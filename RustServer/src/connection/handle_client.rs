use std::net::TcpStream;
use crate::connection::handle_json::handle_json;
use super::send_utf::*;
use super::read_stream::*;
use crate::connection::user::register::*;
use crate::connection::user::login::*;

pub(crate) fn handle_client(mut stream: TcpStream) {
    let mut logged = false;
    loop {
        while !logged{
            let request = read_stream(&mut stream);
            if request.is_empty(){ return };
            println!("Received: {}", request);
            send_utf("registering".to_string(), stream.try_clone().unwrap());
            if request.eq_ignore_ascii_case("1") {
                let registration = read_stream(&mut stream);
                print!("{}", registration);
                register(registration);
                println!("registered, now send json");
                logged = true;
            } else if request.eq_ignore_ascii_case("2") {
                let credentials = read_stream(&mut stream);
                println!("{}", credentials);
                logged = login(credentials);
                println!("{}", logged)
            } else { println!("Error!") }
        }
        let request = read_stream(&mut stream);
        if request.is_empty(){ return };
        println!("Received: {}", request);

        // TODO: handle the message to use different functions
        // Convert received string to json
        let response = handle_json(request, "received.json");

        // The java client needs to read the length of the string first, then the string. (readUTF)
        send_utf(response.to_string(), stream.try_clone().unwrap());
    }
}
