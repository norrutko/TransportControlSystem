package transport.control.service;

import transport.control.model.alert.Alert;

// todo implement interface as one-way connector with Ericson system
public interface Terminal {

    boolean sendMessage(String message);
    boolean sendAllert(Alert alert);
}
