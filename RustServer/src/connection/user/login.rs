use crate::connection::user::get_credentials::{get_password, get_password_from_file, get_username};
use sha2::{Sha256, Digest};

fn get_hash(text :String) -> String{
    // get the hash of the password
    let mut hasher = Sha256::new();
    hasher.update(text);
    // Convert the result of the hasher to String
    return hasher.finalize().iter().map(|b| format!("{:02x}", b)).collect();
}

pub fn login(json :String) -> bool{
    let username = get_username(&json);
    let password = get_password(&json);

    return get_hash(password).eq(&get_hash(get_password_from_file(&username, "users.json")))
}