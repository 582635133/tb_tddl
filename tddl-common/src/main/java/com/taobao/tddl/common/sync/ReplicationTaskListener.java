/*(C) 2007-2012 Alibaba Group Holding Limited.	

public interface ReplicationTaskListener {
    void onTaskCompleted(RowBasedReplicationContext context, boolean success);
}