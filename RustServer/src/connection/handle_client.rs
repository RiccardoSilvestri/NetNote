use std::io::Write;
use std::net::TcpStream;
use std::sync::{Arc, Mutex};
use crate::connection::handle_json::handle_json;
use super::send_utf::*;
use super::read_stream::*;
use crate::connection::user::register::*;
use crate::connection::user::login::*;

pub(crate) fn handle_client(mut stream: TcpStream, file_access: Arc<Mutex<()>>) {
    let mut logged = false;
    let mut return_msg = "Invalid request";
    loop {
        while !logged{
            let request = read_utf(&mut stream);
            if request.is_empty(){ return };
            println!("Received: {}", request);
            send_utf("request received".to_string(), stream.try_clone().unwrap());
            if request.eq_ignore_ascii_case("1") {
                let registration = read_utf(&mut stream);
                println!("registration request: {}", registration);
                let result = register(registration, file_access.clone());
                match result{
                    Ok(_) => {
                        return_msg = "Registration succeeded";
                        logged = true;
                    },
                    Err(e) => println!("Registration failed: {}", e),
                }
                println!("Logged: {}", logged)
            } else if request.eq_ignore_ascii_case("2") {
                let credentials = read_utf(&mut stream);
                println!("login request: {}", credentials);
                let result = login(credentials);
                match result{
                    Ok(_) => {
                        return_msg = "Login succeeded";
                        logged = true;
                    },
                    Err(e) => println!("Login failed: {}", e),
                }
            } else { println!("Invalid request") }
            println!("{}", return_msg);
            println!("Logged: {}", logged);
            let byte_value = logged as u8;
            stream.write(&[byte_value]).expect("can't send boolean to stream");
        }
        let request = read_utf(&mut stream);
        if request.is_empty(){ return };
        println!("Received: {}", request);

        let response = request.clone();
        send_utf(response, stream.try_clone().unwrap());
        match request.as_str(){
            "1" => {
                let request = read_utf(&mut stream);
                if request.is_empty(){ return };
                println!("create a note");
                // create a note
                handle_json(request, "received.json", file_access.clone());
            },
            "2" => {
                // TODO: function to delete a note
            },
            _ => println!("invalid request")
        }
        
        // The java client needs to read the length of the string first, then the string. (readUTF)
    }
}
