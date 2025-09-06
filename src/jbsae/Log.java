package jbsae;

import jbsae.files.*;
import jbsae.files.saves.*;
import jbsae.struct.*;
import jbsae.struct.prim.*;

import static jbsae.util.Stringf.*;

public class Log{
    public static String envar = "JBSAE_LOG_LEVEL";

    public static int maxLogs = 10000; // -1 for unlimited
    public static Queue<LogInfo> logs;

    public static LogLevel level = LogLevel.INFO;

    public static long startTime = 0;

    public static void init(){
        logs = new Queue<>(maxLogs == -1 ? 1000 : maxLogs + 1);
        startTime = System.currentTimeMillis();

        String env = System.getenv(envar);
        if(env != null){
            String setting = env.toUpperCase();
            LogLevel[] levels = LogLevel.values();
            for(LogLevel level : levels) if(level.name().equals(setting)) Log.level = level;
        }
    }

    public static void trace(Object msg){
        log(LogLevel.TRACE, msg);
    }

    public static void debug(Object msg){
        log(LogLevel.DEBUG, msg);
    }

    public static void info(Object msg){
        log(LogLevel.INFO, msg);
    }

    public static void warn(Object msg){
        log(LogLevel.WARN, msg);
    }

    public static void error(Object msg){
        log(LogLevel.ERROR, msg);
    }

    public static void log(LogLevel level, Object msg){
        if(logs == null) return;

        LogInfo info = new LogInfo(level, msg.toString());
        if(level.ordinal() >= Log.level.ordinal()){
            logs.addLast(info);
            System.out.println(info.toString());
            if(maxLogs != -1 && logs.size > maxLogs) logs.removeFirst();
        }
    }

    // TODO: Implement
    public static void writeToFile(String path){
        Fi file = new Fi(path);
        Write write = new Write(file);
        for(LogInfo log : logs) write.str(log.toString());
        write.close();
    }


    public static enum LogLevel{
        TRACE,
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

    public static class LogInfo{
        public LogLevel level;
        public long time;

        public String msg;

        public LogInfo(LogLevel level, String msg){
            this.level = level;
            this.msg = msg;
            this.time = System.currentTimeMillis();
        }

        public String toString(){
            CharSeq result = new CharSeq(msg.length() + 40);
            result.add(formatMillisCompact(time - startTime)).add(' ');
            if(level.name().length() == 4) result.add(' ');
            result.add(level.name()).add(' ').add(msg);
            return result.toString();
        }
    }
}
