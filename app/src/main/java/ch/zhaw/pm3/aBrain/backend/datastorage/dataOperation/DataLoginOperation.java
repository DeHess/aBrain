package ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation;

/**
 * This interface defines all login operations.
 *
 * @author wilphi01
 * @version 1.0
 */
public interface DataLoginOperation {

    int isLoginUser(String email, String password);
    int loginId(String email, String encodeMD5);


}
