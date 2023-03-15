package com.lankahardwared.lankahw.control.download;

import com.lankahardwared.lankahw.control.TaskType;
import com.lankahardwared.lankahw.model.Control;

import java.util.List;

public interface DownloadTaskListener {

    void onTaskCompleted(TaskType taskType, String result);

}
