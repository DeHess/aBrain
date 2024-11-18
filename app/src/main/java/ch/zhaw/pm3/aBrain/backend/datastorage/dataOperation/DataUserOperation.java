package ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation;

import ch.zhaw.pm3.aBrain.backend.User;
import jdk.jshell.spi.ExecutionControl.NotImplementedException;

/**
 * This interface defines all user operations.
 *
 * @author wilphi01
 * @version 1.0
 */
public interface DataUserOperation {

    User getUser(int id);

    void addUser(User user) throws NotImplementedException;

    void deleteUser(User user) throws NotImplementedException;

    void updateUser(User user) throws NotImplementedException;
}
