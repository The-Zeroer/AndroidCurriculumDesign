package com.thezeroer.exercise.android.curriculumdesign.core.enums;

/**
 * 处理路径
 *
 * @author tbrtz647@outlook.com
 * @since 2026/04/13
 * @version 1.0.0
 */
public interface HandlerPath {

    interface Auth {
        short V = 11;
        interface Login {
            short V = 11;
        }
        interface Logout {
            short V = 12;
        }
    }
    short[] Auth_Login = new short[]{Auth.V, Auth.Login.V};
    short[] Auth_Logout = new short[]{Auth.V, Auth.Logout.V};

    interface AccountManagement {
        short V = 21;
        interface Register {
            short V = 11;
        }
        interface Unregister {
            short V = 12;
        }
    }
    short[] AccountManagement_Register = new short[]{AccountManagement.V, AccountManagement.Register.V};
    short[] AccountManagement_Unregister = new short[]{AccountManagement.V, AccountManagement.Unregister.V};

    interface AccountProfile {
        short V = 26;
        interface ChangePassword {
            short V = 101;
        }
        interface ChangeName {
            short V = 111;
        }
        interface ChangeAvatar {
            short V = 112;
        }
        interface GetName {
            short V = 211;
        }
        interface GetAvatar {
            short V = 212;
        }
        interface Info {
            short V = 601;
        }
    }
    short[] AccountProfile_ChangePassword = new short[]{AccountProfile.V, AccountProfile.ChangePassword.V};
    short[] AccountProfile_ChangeName = new short[]{AccountProfile.V, AccountProfile.ChangeName.V};
    short[] AccountProfile_ChangeAvatar = new short[]{AccountProfile.V, AccountProfile.ChangeName.V};
    short[] AccountProfile_GetName = new short[]{AccountProfile.V, AccountProfile.GetName.V};
    short[] AccountProfile_GetAvatar = new short[]{AccountProfile.V, AccountProfile.GetName.V};
    short[] AccountProfile_Info = new short[]{AccountProfile.V, AccountProfile.Info.V};

    interface Contacts {
        short V = 31;
        interface GetAll {
            short V = 11;
        }
    }
    short[] Contacts_GetAll = new short[]{Contacts.V, Contacts.GetAll.V};

    interface Audits {
        short V = 41;
        interface GetList {
            short V = 11;
        }
        interface GetContent {
            short V = 12;
        }
    }
    short[] Audits_GetList = new short[]{Audits.V, Audits.GetList.V};
    short[] Audits_GetContent = new short[]{Audits.V, Audits.GetContent.V};

    interface Message {
        short V = 101;
        interface Push {
            short V = 111;
        }
        interface Pull {
            short V = 121;
        }
    }
    short[] Message_Push = new short[] {Message.V, Message.Push.V};
    short[] Message_Pull = new short[] {Message.V, Message.Pull.V};
}
