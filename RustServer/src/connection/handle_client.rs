use std::io::Write;
use std::net::TcpStream;
use std::sync::{Arc, Mutex};
use colored::Colorize;
use super::send_utf::*;
use super::read_stream::*;
use crate::connection::user::register::*;
use crate::connection::user::login::*;
use crate::connection::utils::send_user_notes::send_user_notes;
use super::notes::remove_note::remove_note;
use super::notes::add_note::add_note;
use super::user::get_credentials::{get_value_from_json};
use super::utils::create_if_not_exists::*;

pub(crate) fn handle_client(mut stream: TcpStream, file_access: Arc<Mutex<()>>) {
    let notes_file = "received.json";
    let users_file = "users.json";
    let mut logged = false;
    let mut return_msg = "Invalid request";
    let mut user = "".to_string();
    while !logged{
        let request = read_utf(&mut stream);
        if request.is_empty(){ return };
        println!("{} {}", "Recieved message:".bold(), request.italic());

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
        // send boolean to the client
        stream.write(&[byte_value]).expect("can't send boolean to stream");
        let _ = match get_value_from_json("name", credentials.clone().as_str()) {
            Ok(got_name) => {user = got_name}
            Err(e) => {println!("Error getting user name: {}", e)}
        };
    }
    println!("Logged user: {}", user);

    // if the notes file doesn't exist, create it
    match create_if_not_exists(notes_file){
        Ok(_) => println!("File exists or was created successfully."),
        Err(e) => println!("An error occurred: {}", e),
    }
    loop {
        // first I let the user select the action to take, then I ask for the json of the note
        let option = read_utf(&mut stream);
        if option.is_empty(){ return };
        send_user_notes(notes_file, user.clone(), file_access.clone(), stream.try_clone().unwrap());
        println!("Sent user notes");

        println!("{} {}", "Option: ".bold(), option);
        match option.as_str() {
            "0" => {},
            "1" => {
                let request = read_utf(&mut stream);
                println!("{} {}", "Recieved message:".bold(), request.italic());
                // create a note
                // if request.is_empty() { return };
                println!("create a note");
                match add_note(notes_file, &*request, file_access.clone()){
                    Ok(_) => {println!("Note created")}
                    Err(e) => {println!("{}", e)}
                };
            },
            "2" => {
                let request = read_utf(&mut stream);
                println!("{} {}", "Recieved message:".bold(), request.italic());
                // the client should send a json containing only author and title
                // if request.is_empty() { return };
                match remove_note(notes_file, &*request, file_access.clone()){
                    Ok(_) => {println!("Note deleted")}
                    Err(e) => {println!("{}", e)}
                }
            },
            _ => println!("invalid request")
        }
    }
}
