use std::net::TcpStream;
use std::sync::{Arc, Mutex};
use colored::Colorize;
use crate::connection::notes::filter_by_author::filter_by_author;
use crate::connection::send_utf::send_utf;
use crate::connection::utils::check_json_file::check_json_file;

pub fn send_user_notes(notes_file :&str, user :String, file_access :Arc<Mutex<()>>, stream: TcpStream){
    // send all user's notes to the client
    let mut response = "no notes".to_string();
    // if the file is a valid json, filter it by logged user
    match check_json_file(notes_file){
        Ok(_) => {
            println!("The json file is valid");
            response = filter_by_author(notes_file, user.as_str(), file_access.clone()).unwrap().to_string();
        }
        Err(e) => println!("An error occurred: {}", e),
    }
    println!("{} {}", "Sent user notes to client: ".bold().green(), response);
    send_utf(response, stream.try_clone().unwrap());
}