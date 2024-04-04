# NetNote

A TCP Note-Taking application written in Rust and Java.

![](C:\Users\FrancescoLanza\OneDrive%20-%20ITS%20Angelo%20Rizzoli\Documents\Projects\UFS-02\misc\banner.png)

# Server

The server is in charge of handling users and notes, and storing them to file.

# Client

The client makes the user create, delete and edit notes.

```mermaid
sequenceDiagram
    participant Server
    participant Client

    Client->>Server: LoginOption
    Server->>Client: "Request Received"
    Client->>Server: CredentialsJson
    Server->>Client: logged (bool)
    Client->>Server: NoteOption
    Server->>Client: NotesOfAuthor
    Client->>Server: Note
    Server->>Client: NotesOfAuthor
    Note over Client, Server: Back to NoteOption
```
