package ch.zhaw.pm3.aBrain.backend.datastorage.dataOperation;

/**
 * This interface defines all logger operations.
 *
 * @author wilphi01
 * @version 1.0
 */
public interface DataLoggerOperation {

    enum LogType {
        INFO(0),
        WARNING(1),
        ERROR(2),
        DEBUG(3);

        private final int id;

        LogType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
    void writeLog(String log, LogType logType);

}
