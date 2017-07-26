package com.td.common.util;


/**
 * @author yangtao
 */
public class HadoopUtil {
//
//	/**
//	 * 
//	 */
//	private static HadoopUtil instance;
//
//	/**
//	 * 访问job client
//	 */
//	private JobClient jobClient;
//
//	private HadoopUtil() throws Exception {
//		// do nothging
//		init();
//	}
//
//	public static synchronized HadoopUtil getInstance() throws Exception {
//		if (instance == null) {
//			instance = new HadoopUtil();
//		}
//		return instance;
//	}
//
//	/**
//	 * 初始化jobClient
//	 * @throws IOException 
//	 */
//	private void init() throws Exception {
//		SysParam hotsParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SEND_HOST_IP);
//		SysParam ipParam = (SysParam) SysCacheWrapper.getValue(CacheConstant.CACHE_NAME_SYSTEM_PARAM, CommonConstant.SEND_HOST_PORT);
//		int port = Integer.valueOf(ipParam.getParamvalue());
//		//		conf.set("mapred.job.tracker", "hadoop01.jkdc:9001");
//		jobClient = new JobClient(new InetSocketAddress(hotsParam.getParamvalue(), port), new Configuration());
//	}
//
////	/**
////	 * 提交job
////	 * @param activityId 活动id
////	 * @param sendRoundId 发送轮次id
////	 * @param serialNumber 序号,对应预处理结果文件的序号
////	 * @return
////	 * @throws Exception
////	 */
////	public RunningJob submitJob(long activityId, long sendRoundId, int serialNumber) throws Exception {
////		JobConf conf = new JobConf();
////		conf.set("fs.default.name", "hdfs://hadoop01.jkdc:9000");
////		conf.set("mapred.job.tracker", "hadoop01.jkdc:9001");
////		String jobName = "SMS" + "_" + activityId + "_" + sendRoundId;
////		conf.setJobName(jobName);
////		String[] otherArgs = new String[] { "/user/test/in", "/user/test/out" };
////		FileSystem fs = FileSystem.get(conf);
////		boolean flag = fs.delete(new Path(otherArgs[1]), true);
////		System.out.println("delete====" + flag);
////		DistributedCache.addFileToClassPath(new Path("/user/test/hadooptest.jar"), conf, fs);
////		Job job = new Job(conf, jobName);
//////		job.setJarByClass(WordCountJob.class);
//////		job.setMapperClass(WordCountMapper.class);
//////		job.setCombinerClass(WordCountReducer.class);
//////		job.setReducerClass(WordCountReducer.class);
////		job.setOutputKeyClass(Text.class);
////		job.setOutputValueClass(IntWritable.class);
////		FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
////		String dirStr = StringUtils.escapeString(new Path(otherArgs[0]).toString());
////		conf.set("mapred.input.dir", dirStr);
////		FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
////		conf.set("mapred.output.dir", StringUtils.escapeString(new Path(otherArgs[1]).toString()));
////		RunningJob runningJob = jobClient.submitJob(conf);
////		//		System.out.println("jtIdentifier=" + runningJob.getID().getJtIdentifier() + " id=" + runningJob.getID().getId());
////		return runningJob;
////	}
//
//	/**
//	 * @param jobId 格式为：job_201402241724_0133
//	 * @return 
//	 * @throws Exception
//	 * public static final int RUNNING = 1;
//	 * public static final int SUCCEEDED = 2;
//	 * public static final int FAILED = 3;
//	 * public static final int PREP = 4;
//	 * public static final int KILLED = 5;
//	 */
//	public int queryHadoopJobState(String jobId) throws Exception {
//		String[] jobIdSplits = jobId.split("_", 3);
//		return queryHadoopJobState(jobIdSplits[1], Integer.valueOf(jobIdSplits[2]));
//	}
//
//	/**
//	 * 查询hadoop job的状态
//	 * @param jtIdentifier
//	 * @param id
//	 * @return
//	 * @throws Exception
//	 * public static final int RUNNING = 1;
//	 * public static final int SUCCEEDED = 2;
//	 * public static final int FAILED = 3;
//	 * public static final int PREP = 4;
//	 * public static final int KILLED = 5;
//	 */
//	public int queryHadoopJobState(String jtIdentifier, int id) throws Exception {
//		RunningJob runningJob = jobClient.getJob(new JobID(jtIdentifier, id));
//		if (runningJob == null) {
//			throw new CommonException("未找到该hadoop job,jtIdentifier=" + jtIdentifier + " id=" + id);
//		}
//		return runningJob.getJobState();
//	}
//
//	/**
//	 * 查找hadoop job
//	 * @param jobId
//	 * @return RunningJob
//	 * @throws Exception
//	 */
//	public RunningJob queryHadoopRunningJob(String jobId) throws Exception {
//		String[] jobIdSplits = jobId.split("_", 3);
//		return queryHadoopRunningJob(jobIdSplits[1], Integer.valueOf(jobIdSplits[2]));
//	}
//
//	/**
//	 * 查找hadoop job
//	 * @param jtIdentifier
//	 * @param id
//	 * @return RunningJob
//	 * @throws Exception
//	 */
//	public RunningJob queryHadoopRunningJob(String jtIdentifier, int id) throws Exception {
//		RunningJob runningJob = jobClient.getJob(new JobID(jtIdentifier, id));
//		if (runningJob == null) {
//			throw new CommonException("未找到该hadoop job,jtIdentifier=" + jtIdentifier + " id=" + id);
//		}
//		return runningJob;
//	}
//
//	/**
//	 * @param jobId
//	 * @return
//	 * @throws Exception 
//	 * @throws NumberFormatException 
//	 */
//	public int querySmsHadoopSendJobStatus(String jobId) throws NumberFormatException, Exception {
//		String[] jobIdSplits = jobId.split("_", 3);
//		return queryHadoopSendJobStatus(jobIdSplits[1], Integer.valueOf(jobIdSplits[2]));
//	}
//
//	/**
//	 * @param jtIdentifier
//	 * @param id 
//	 * @return
//	 * @throws Exception 
//	 */
//	public int queryHadoopSendJobStatus(String jtIdentifier, int id) throws Exception {
//		int state = queryHadoopJobState(jtIdentifier, id);
//		if (state == JobStatus.PREP) {
//			return CommonConstant.HADOOP_SEND_JOB_STATUS_INCOMPLATE;
//		}
//		else if (state == JobStatus.RUNNING) {
//			return CommonConstant.HADOOP_SEND_JOB_STATUS_RUNNING;
//		}
//		else if (state == JobStatus.KILLED) {
//			return CommonConstant.HADOOP_SEND_JOB_STATUS_EXCEPTION;
//		}
//		else if (state == JobStatus.SUCCEEDED) {
//			return CommonConstant.HADOOP_SEND_JOB_STATUS_COMPLETE;
//		}
//		else if (state == JobStatus.FAILED) {
//			return CommonConstant.HADOOP_SEND_JOB_STATUS_EXCEPTION;
//		}
//		return state;
//	}
//
//	public static void parseHeader(String pageHeader, Map<String, Object> contextMap){
//		String[] s = pageHeader.split(" encoding=");
//		if (s.length == 2){
//			contextMap.put("uuid", s[0].trim());
//			s = s[1].split(" json=");
//			if (s.length == 2){
//				contextMap.put("encoding", s[0].trim());
//				JSONObject jsobject = JSONObject.fromObject(s[1].trim());// 根据字符串转换对象
//				HtmlPageMetadata htmlPageMetadata = (HtmlPageMetadata) JSONObject.toBean(jsobject, HtmlPageMetadata.class);
//				contextMap.put("htmlPageMetadata", htmlPageMetadata);
//			}
//		}
//	}
//	
//	/**
//	 * @param args
//	 * @throws Exception
//	 */
////	@SuppressWarnings("deprecation")
////	public static void main(String[] args) throws Exception {
////		ClassPathXmlApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
////		ApplicationContextManager.setAppContext(cxt);
////		RunningJob runningJob = HadoopUtil.getInstance().submitJob(0, 0, 0);
////		System.out.println("jobId = " + runningJob.getJobID());
////		System.out.println("jtIdentifier = " + runningJob.getID().getJtIdentifier() + " id=" + runningJob.getID().getId());
////		//		jobIdjob_201402241724_0131
////		//		jtIdentifier=201402241724 id=131
////		//		jobIdjob_201402241724_0131
////		//		jtIdentifier201402241724 id=131
////		int i = HadoopUtil.getInstance().queryHadoopJobState("201402241724", 131);
////		System.out.println("job_201402241724_0131 queryHadoopJobState = " + i);
////	}
//
//	public static void main(String[] args) {
//		
//	}
	
}
