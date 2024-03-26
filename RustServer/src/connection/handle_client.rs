use std::io::Write;
use std::net::TcpStream;
use std::sync::{Arc, Mutex};
use super::send_utf::*;
use super::read_stream::*;
use crate::connection::user::register::*;
use crate::connection::user::login::*;
use crate::connection::notes::filter_by_author::*;
use super::notes::remove_note::remove_note;
use super::notes::create_note::create_note;
use super::user::get_credentials::{get_value_from_json};
use super::utils::create_if_not_exists::*;

pub(crate) fn handle_client(mut stream: TcpStream, file_access: Arc<Mutex<()>>) {
    let notes_file = "received.json";
    let users_file = "users.json";
    let mut logged = false;
    let mut return_msg = "Invalid request";
    let mut user = "".to_string();
    loop {
        while !logged{
            let request = read_utf(&mut stream);
            if request.is_empty(){ return };
            println!("Received: {}", request);
            send_utf("request received".to_string(), stream.try_clone().unwrap());
            // now get credentials from client
            let credentials = read_utf(&mut stream);
            if request.eq_ignore_ascii_case("1") {
                println!("registration request: {}", credentials);
                let result = register(credentials.clone(), users_file, file_access.clone());
                match result{
                    Ok(_) => {
                        return_msg = "Registration succeeded";
                        logged = true;
                    },
                    Err(e) => println!("Registration failed: {}", e),
                }
                println!("Logged: {}", logged)
            } else if request.eq_ignore_ascii_case("2") {
                println!("login request: {}", credentials);
                let result = login(credentials.clone());
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
            let _ = match get_value_from_json("name", credentials.clone().as_str()) {
                Ok(got_name) => {user = got_name}
                Err(e) => {println!("Error getting user name: {}", e)}
            };
        }
        println!("Logged user: {}", user);
        let request = read_utf(&mut stream);
        if request.is_empty(){ return };
        println!("Received: {}", request);

        // if the file received.json doesn't exist, create it
        match create_if_not_exists(notes_file){
            Ok(_) => println!("File exists or was created successfully."),
            Err(e) => println!("An error occurred: {}", e),
        }
        loop {
            // send all user's notes to the client
            let response = filter_by_author(notes_file, "Paolo", file_access.clone()).unwrap().to_string();
            println!("{}", response);
            send_utf(response, stream.try_clone().unwrap());
            match request.as_str() {
                "1" => {
                    // create a note
                    let request = read_utf(&mut stream);
                    if request.is_empty() { return };
                    println!("create a note");
                    create_note(notes_file, &*request, file_access.clone()).unwrap();
                },
                "2" => {
                    // the client should send a json containing only author and title
                    let request = read_utf(&mut stream);
                    if request.is_empty() { return };
                    remove_note(notes_file, &*request, file_access.clone()).unwrap()
                },
                _ => println!("invalid request")
            }
        }
    }
}
