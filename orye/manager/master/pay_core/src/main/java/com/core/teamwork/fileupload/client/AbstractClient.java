package com.core.teamwork.fileupload.client;

import com.core.teamwork.fileupload.command.AbstractCmd;

/**
 * 提供�?些公用的的方�?
 * Created by sunbaoming on 2014/6/23.
 */
public abstract class AbstractClient {

    protected String[] splitFileId(String fileid) {
        return AbstractCmd.splitFileId(fileid);
    }
}
