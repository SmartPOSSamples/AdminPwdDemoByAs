package com.wizarpos.wizarviewagentassistant.aidl;
interface IModifyAdminPwdService{
   boolean modifyAdminPwd(String oldPwd, String newPwd);
   boolean isAdminPwd(String pwd);
   /**
   * Only system applications can call this method
   * */
   //@Deprecated
   boolean reset(String pwd);
   boolean forceModifyAdminPwd(String newPwd);
}