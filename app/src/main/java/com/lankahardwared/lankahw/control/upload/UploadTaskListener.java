package com.lankahardwared.lankahw.control.upload;


import com.lankahardwared.lankahw.control.TaskType;

import java.util.List;

public interface UploadTaskListener
{
    void onTaskCompleted(TaskType taskType, List<String> list);
}