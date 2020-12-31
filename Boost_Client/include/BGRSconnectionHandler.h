//
// Created by Amir Zaushnizer on 31/12/2020.
//

#ifndef BOOST_CLIENT_BGRSCONNECTIONHANDLER_H
#define BOOST_CLIENT_BGRSCONNECTIONHANDLER_H
#include <string>
#include <iostream>
#include <boost/asio.hpp>

using boost::asio::ip::tcp;

class BGRSconnectionHandler {
private:
    const std::string host_;
    const short port_;
    boost::asio::io_service io_service_;   // Provides core I/O functionality
    tcp::socket socket_;

public:
    BGRSconnectionHandler(std::string host, short port);
    virtual ~BGRSconnectionHandler();

    // Connect to the remote machine
    bool connect();

    // Read a fixed number of bytes from the server - blocking.
    // Returns false in case the connection is closed before bytesToRead bytes can be read.
    bool getBytes(char bytes[], unsigned int bytesToRead);

    // Send a fixed number of bytes from the client - blocking.
    // Returns false in case the connection is closed before all the data is sent.
    bool sendBytes(const char bytes[], int bytesToWrite);

    bool sendMessage(std::string& msg);


    // Close down the connection properly.
    void close();

}; //class ConnectionHandler

#endif
