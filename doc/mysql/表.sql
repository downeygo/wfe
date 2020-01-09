alter table oauth_role_details add `add_user` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
alter table oauth_role_details add`edit_user` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL;
alter table oauth_role_details add `add_time` datetime(0) NOT NULL;
alter table oauth_role_details add `edit_time` datetime(0) NOT NULL;
alter table oauth_role_details add`is_deleted` int(11) NOT NULL DEFAULT 0;