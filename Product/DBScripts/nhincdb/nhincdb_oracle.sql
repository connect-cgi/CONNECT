--------------------------------------------------------
--  File created - Tuesday-February-25-2014   
--------------------------------------------------------

 CREATE USER nhincuser identified by nhincpass;
 
 GRANT RESOURCE,CONNECT to nhincuser;
 
 CREATE USER configuser identified by configpass;
  
 GRANT RESOURCE,CONNECT to configuser;
 
--------------------------------------------------------
--  DDL for Sequence HIBERNATE_SEQUENCE
--------------------------------------------------------

   CREATE SEQUENCE  "NHINCUSER"."HIBERNATE_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 2961 CACHE 20 NOORDER  NOCYCLE ;
   CREATE SEQUENCE  "CONFIGUSER"."HIBERNATE_SEQUENCE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 2961 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table AA_TO_HOME_COMMUNITY_MAPPING
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."AA_TO_HOME_COMMUNITY_MAPPING" 
   (	"ID" NUMBER(10,0), 
	"ASSIGNINGAUTHORITYID" VARCHAR2(64 BYTE), 
	"HOMECOMMUNITYID" VARCHAR2(64 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ADDRESS
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."ADDRESS" 
   (	"ADDRESSID" NUMBER(11,0), 
	"PATIENTID" NUMBER(11,0), 
	"STREET1" VARCHAR2(128 BYTE), 
	"STREET2" VARCHAR2(128 BYTE), 
	"CITY" VARCHAR2(128 BYTE), 
	"STATE" VARCHAR2(128 BYTE), 
	"POSTAL" VARCHAR2(45 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table ASYNCMSGREPO
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."ASYNCMSGREPO" 
   (	"ID" NUMBER(10,0), 
	"MESSAGEID" VARCHAR2(100 BYTE), 
	"CREATIONTIME" DATE, 
	"RESPONSETIME" DATE, 
	"DURATION" NUMBER(10,0), 
	"SERVICENAME" VARCHAR2(45 BYTE), 
	"DIRECTION" VARCHAR2(10 BYTE), 
	"COMMUNITYID" VARCHAR2(100 BYTE), 
	"STATUS" VARCHAR2(45 BYTE), 
	"RESPONSETYPE" VARCHAR2(10 BYTE), 
	"RESERVED" VARCHAR2(100 BYTE), 
	"MSGDATA" BLOB, 
	"RSPDATA" BLOB, 
	"ACKDATA" BLOB
   ) ;
--------------------------------------------------------
--  DDL for Table AUDITREPOSITORY
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."AUDITREPOSITORY" 
   (	"ID" NUMBER, 
	"AUDIT_TIMESTAMP" DATE, 
	"EVENTID" NUMBER, 
	"USERID" VARCHAR2(100 BYTE), 
	"PARTICIPATIONTYPECODE" NUMBER, 
	"PARTICIPATIONTYPECODEROLE" NUMBER, 
	"PARTICIPATIONIDTYPECODE" VARCHAR2(100 BYTE), 
	"RECEIVERPATIENTID" VARCHAR2(128 BYTE), 
	"SENDERPATIENTID" VARCHAR2(128 BYTE), 
	"COMMUNITYID" VARCHAR2(255 BYTE), 
	"MESSAGETYPE" VARCHAR2(100 BYTE), 
	"MESSAGE" BLOB
   ) ;
--------------------------------------------------------
--  DDL for Table CORRELATEDIDENTIFIERS
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."CORRELATEDIDENTIFIERS" 
   (	"CORRELATIONID" NUMBER(10,0), 
	"PATIENTASSIGNINGAUTHORITYID" VARCHAR2(64 BYTE), 
	"PATIENTID" VARCHAR2(128 BYTE), 
	"CORRELATEDPATIENTASSIGNAUTHID" VARCHAR2(64 BYTE), 
	"CORRELATEDPATIENTID" VARCHAR2(128 BYTE), 
	"CORRELATIONEXPIRATIONDATE" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table DOCUMENT
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."DOCUMENT" 
   (	"DOCUMENTID" NUMBER(11,0), 
	"DOCUMENTUNIQUEID" VARCHAR2(64 BYTE), 
	"DOCUMENTTITLE" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"AUTHORPERSON" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"AUTHORINSTITUTION" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"AUTHORROLE" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"AUTHORSPECIALTY" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"AVAILABILITYSTATUS" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"CLASSCODE" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"CLASSCODESCHEME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"CLASSCODEDISPLAYNAME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"CONFIDENTIALITYCODE" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"CONFIDENTIALITYCODESCHEME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"CONFIDENTIALITYCODEDISPLAYNAME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"CREATIONTIME" DATE DEFAULT NULL, 
	"FORMATCODE" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"FORMATCODESCHEME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"FORMATCODEDISPLAYNAME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"PATIENTID" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"SERVICESTARTTIME" DATE DEFAULT NULL, 
	"SERVICESTOPTIME" DATE DEFAULT NULL, 
	"STATUS" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"COMMENTS" VARCHAR2(256 BYTE) DEFAULT NULL, 
	"HASH" VARCHAR2(1028 BYTE) DEFAULT NULL, 
	"FACILITYCODE" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"FACILITYCODESCHEME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"FACILITYCODEDISPLAYNAME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"INTENDEDRECIPIENTPERSON" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"INTENDEDRECIPIENTORGANIZATION" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"LANGUAGECODE" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"LEGALAUTHENTICATOR" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"MIMETYPE" VARCHAR2(32 BYTE) DEFAULT NULL, 
	"PARENTDOCUMENTID" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"PARENTDOCUMENTRELATIONSHIP" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"PRACTICESETTING" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"PRACTICESETTINGSCHEME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"PRACTICESETTINGDISPLAYNAME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"DOCUMENTSIZE" NUMBER(11,0) DEFAULT NULL, 
	"SOURCEPATIENTID" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"PID3" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"PID5" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"PID7" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"PID8" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"PID11" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"TYPECODE" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"TYPECODESCHEME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"TYPECODEDISPLAYNAME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"DOCUMENTURI" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"RAWDATA" BLOB, 
	"PERSISTENT" NUMBER(11,0), 
	"ONDEMAND" NUMBER(1,0), 
	"NEWDOCUMENTUNIQUEID" VARCHAR2(128 BYTE) DEFAULT NULL, 
	"NEWREPOSITORYUNIQUEID" VARCHAR2(128 BYTE) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table EVENT
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."EVENT" 
   (	"ID" NUMBER(11,0), 
	"NAME" VARCHAR2(100 BYTE), 
	"TRANSACTIONID" VARCHAR2(100 BYTE), 
	"MESSAGEID" VARCHAR2(100 BYTE),
	"SERVICETYPE" VARCHAR2(100 BYTE),
	"INITIATINGHCID" VARCHAR2(100 BYTE),
	"RESPONDINGHCIDS" VARCHAR2(100 BYTE),
	"EVENTTIME" DATE, 
	"DESCRIPTION" VARCHAR2(4000 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table EVENTCODE
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."EVENTCODE" 
   (	"EVENTCODEID" NUMBER(11,0), 
	"DOCUMENTID" NUMBER(11,0), 
	"EVENTCODE" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"EVENTCODESCHEME" VARCHAR2(64 BYTE) DEFAULT NULL, 
	"EVENTCODEDISPLAYNAME" VARCHAR2(64 BYTE) DEFAULT NULL
   ) ;
--------------------------------------------------------
--  DDL for Table IDENTIFIER
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."IDENTIFIER" 
   (	"IDENTIFIERID" NUMBER(11,0), 
	"PATIENTID" NUMBER(11,0), 
	"ID" VARCHAR2(64 BYTE), 
	"ORGANIZATIONID" VARCHAR2(64 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table PATIENT
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."PATIENT" 
   (	"PATIENTID" NUMBER(11,0), 
	"DATEOFBIRTH" DATE, 
	"GENDER" CHAR(2 BYTE), 
	"SSN" CHAR(9 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table PDDEFERREDCORRELATION
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."PDDEFERREDCORRELATION" 
   (	"ID" NUMBER(11,0), 
	"MESSAGEID" VARCHAR2(100 BYTE), 
	"ASSIGNINGAUTHORITYID" VARCHAR2(64 BYTE), 
	"PATIENTID" VARCHAR2(128 BYTE), 
	"CREATIONTIME" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table PERSONNAME
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."PERSONNAME" 
   (	"PERSONNAMEID" NUMBER(11,0), 
	"PATIENTID" NUMBER(11,0), 
	"PREFIX" VARCHAR2(64 BYTE), 
	"FIRSTNAME" VARCHAR2(64 BYTE), 
	"MIDDLENAME" VARCHAR2(64 BYTE), 
	"LASTNAME" VARCHAR2(64 BYTE), 
	"SUFFIX" VARCHAR2(64 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table PHONENUMBER
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."PHONENUMBER" 
   (	"PHONENUMBERID" NUMBER(11,0), 
	"PATIENTID" NUMBER(11,0), 
	"VALUE" VARCHAR2(64 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table TRANSACTIONREPOSITORY
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."TRANSACTIONREPOSITORY" 
   (	"ID" NUMBER(11,0), 
	"TRANSACTIONID" VARCHAR2(100 BYTE), 
	"MESSAGEID" VARCHAR2(100 BYTE), 
	"TRANSACTIONTIME" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table TRANSFER_DATA
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."TRANSFER_DATA" 
   (	"ID" NUMBER, 
	"REQUESTKEYGUID" VARCHAR2(64 BYTE), 
	"TRANSFERSTATE" VARCHAR2(32 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for Table USERLOGIN
--------------------------------------------------------

  CREATE TABLE "NHINCUSER"."USERLOGIN"
   (	"ID" NUMBER(19,0),
	"SALT" VARCHAR2(100 BYTE),
	"SHA1" VARCHAR2(100 BYTE),
	"USERNAME" VARCHAR2(100 BYTE),
	"USERROLE" NUMBER(19,0) NOT NULL
   ) ;
   
--------------------------------------------------------
--  DDL for Table Domain
--------------------------------------------------------
  CREATE TABLE "CONFIGUSER"."DOMAIN"
  ( "ID" NUMBER(19,0),
	"POSTMASTERADDRESSID" NUMBER(19, 0),
    "DOMAINNAME" VARCHAR2(255 BYTE),
    "STATUS" NUMBER(10, 0) DEFAULT 0,
    "CREATETIME" DATE,
    "UPDATETIME" DATE
	);
	
-- -----------------------------------------------------
-- DDL for Table ADDRESS
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."ADDRESS" (
    "ID" NUMBER(19,0),
    "EMAILADDRESSS" VARCHAR2(255 BYTE) NOT NULL,
    "DISPLAYNAME" VARCHAR2(100 BYTE),
    "ENDPOINT" VARCHAR2(255 BYTE),
    "TYPE" VARCHAR2(10 BYTE),
    "STATUS" NUMBER(10, 0) DEFAULT 0,
    "CREATETIME" DATE,
    "UPDATETIME" DATE,
    "DOMAINID" NUMBER(19, 0) NOT NULL  
);

-- -----------------------------------------------------
-- DDL for Table ANCHOR
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."ANCHOR" (
    "ID" NUMBER(19, 0),
    "CERTIFICATEID" NUMBER(19, 0),
    "OWNER" VARCHAR2(255 BYTE),
    "THUMBPRINT" VARCHAR2(64 BYTE),
    "CERTIFICATEDATA" BLOB,
    "VALIDSTARTDATE" DATE,
    "VALIDENDDATE" DATE,
    "INCOMING" NUMBER(1)  DEFAULT 1,
    "OUTGOING" NUMBER(1)  DEFAULT 1,
    "STATUS" NUMBER(10, 0) DEFAULT 0,
    "CREATETIME" DATE 
);

-- -----------------------------------------------------
-- DDL for Table CERTIFICATE
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."CERTIFICATE" (
    "ID" NUMBER(19,0),
    "OWNER" VARCHAR2(255 BYTE),
    "THUMBPRINT" VARCHAR2(64 BYTE),
    "CERTIFICATEDATA" BLOB,
    "VALIDSTARTDATE" DATE,
    "VALIDENDDATE" DATE ,
    "PRIVATEKEY" NUMBER(1)  DEFAULT 0,
    "STATUS" NUMBER(10, 0) DEFAULT 0,
    "CREATETIME" DATE 
);

-- -----------------------------------------------------
-- DDL for Table SETTING
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."SETTING" (
    "ID" NUMBER(19,0),
    "NAME" VARCHAR2(255 BYTE),
    "VALUE" VARCHAR2(4000 BYTE),
    "STATUS" NUMBER(10, 0) DEFAULT 0,
    "CREATETIME" DATE NOT NULL,
    "UPDATETIME" DATE
);

-- -----------------------------------------------------
-- DDL for Table TRUSTBUNDLE
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."TRUSTBUNDLE" (
    "ID" NUMBER(19,0),
    "BUNDLENAME" VARCHAR2(255 BYTE),
    "BUNDLEURL" VARCHAR2(255 BYTE),
    "BUNDLECHECKSUM" VARCHAR2(255 BYTE),
    "LASTREFRESHATTEMPT" DATE,
    "LASTSUCCESSFULREFRESH" DATE,
    "REFRESHINTERVAL" NUMBER(10, 0),
    "LASTREFRESHERROR" NUMBER(10, 0),
    "SIGNINGCERTIFICATEDATA" BLOB,
    "CREATETIME" DATE 
);

-- -----------------------------------------------------
-- DDL for Table TRUSTBUNDLEANCHOR
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."TRUSTBUNDLEANCHOR" (
    "ID" NUMBER(19,0),
    "ANCHORDATA" BLOB,
    "THUMBPRINT" VARCHAR2(64 BYTE),
    "VALIDSTARTDATE" DATE,
    "VALIDENDDATE" DATE,
    "TRUSTBUNDLEID" NUMBER(19,0) 
);

-- -----------------------------------------------------
-- DDL for Table TRUSTBUNDLEDOAMINRELTN
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."TRUSTBUNDLEDOMAINRELTN" (
    "ID" NUMBER(19,0),
    "INCOMING" NUMBER(1) DEFAULT 1,
    "OUTGOING" NUMBER(1) DEFAULT 1,
    "DOMAIN_ID" NUMBER(19,0),
    "TRUST_BUNDLE_ID" NUMBER(19,0) 
);

-- -----------------------------------------------------
-- DDL for Table CERTPOLICY
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."CERTPOLICY" (
    "ID" NUMBER(19,0),
    "CREATETIME" DATE,
    "LEXICON" NUMBER(19,0),
    "POLICYDATA" BLOB,
    "POLICYNAME" VARCHAR2(255 BYTE)
);

-- -----------------------------------------------------
-- Table `configdb`.`certpolicygroup`
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."CERTPOLICYGROUP" (
    "ID" NUMBER(19,0),
    "CREATETIME" DATE ,
    "POLICYGROUPNAME" VARCHAR2(255 BYTE)
);

-- -----------------------------------------------------
-- Table `configdb`.`certpolicygroupdomainreltn`
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."CERTPOLICYGROUPDOMAINRELTN" (
    "ID" NUMBER(19,0),
    "POLICY_GROUP_ID" NUMBER(19,0),
    "DOMAIN_ID" NUMBER(19,0) NOT NULL
);

-- -----------------------------------------------------
-- Table `configdb`.`certpolicygroupreltn`
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."CERTPOLICYGROUPRELTN" (
    "ID" NUMBER(19,0),
    "INCOMING" NUMBER(4,0),
    "OUTGOING" NUMBER(4,0),
    "POLICYUSE" NUMBER(10,0) ,
    "CERTPOLICYID" NUMBER(19,0),
    "CERTPOLICYGROUPID" NUMBER(19,0)
);

-- -----------------------------------------------------
-- Table `configdb`.`dnsrecord`
-- -----------------------------------------------------

CREATE TABLE "CONFIGUSER"."DNSRECORD" (
    "ID" NUMBER(19,0),
    "CREATETIME" DATE NOT NULL,
    "DATA" BLOB,
    "DCLASS" NUMBER(10,0),
    "NAME" VARCHAR2(255 BYTE),
    "TTL" NUMBER(19,0),
    "TYPE" NUMBER(10,0)
);

-- end configdb

------------------------------------------------------------------
--   ADMINGUIDB TABLES
------------------------------------------------------------------
-- -----------------------------------------------------
-- Table USERROLE
-- -----------------------------------------------------
CREATE TABLE "NHINCUSER"."USERROLE" (
	"ROLEID" NUMBER(19,0),
	"ROLENAME" VARCHAR2(100 BYTE) NOT NULL
);

-- -----------------------------------------------------
-- Table PagePreference
-- -----------------------------------------------------

CREATE TABLE "NHINCUSER"."PAGEPREFERENCE" (
	"PREFID" NUMBER(19,0),
	"PAGENAME"   VARCHAR2(100 BYTE) NOT NULL,
	"PAGEDESC"  VARCHAR2(100 BYTE) NOT NULL,
	"ACCESSPAGE" NUMBER(19,0) NOT NULL,
	"PREFROLEID" NUMBER(19,0) NOT NULL
);

CREATE TABLE "NHINCUSER"."MONITOREDMESSAGE" (
  "ID" NUMBER(19,0),
  "SENDEREMAILID"  VARCHAR2(255 BYTE) DEFAULT NULL,
  "SUBJECT"  VARCHAR2(255 BYTE) DEFAULT NULL,
  "MESSAGEID"   VARCHAR2(100 BYTE),
  "RECIPIENTS"  VARCHAR2(4000 BYTE) DEFAULT NULL,
  "DELIVERYREQUESTED" NUMBER(3) DEFAULT '0',
  "STATUS" VARCHAR2(30 BYTE) DEFAULT NULL,
  "CREATETIME" DATE DEFAULT NULL,
  "UPDATETIME" DATE DEFAULT NULL
);


CREATE TABLE "NHINCUSER"."MONITOREDMESSAGENOTIFICATION" (
  "ID" NUMBER(19,0),
  "EMAILID" VARCHAR2(255) NOT NULL,
  "MESSAGEID" VARCHAR2(100) DEFAULT NULL,
  "MONITOREDMESSAGEID" NUMBER(19,0) NOT NULL,
  "STATUS" VARCHAR2(30) ,
  "CREATETIME" DATE DEFAULT NULL,
  "UPDATETIME" DATE DEFAULT NULL
);

--------------------------------------------------------
--  Constraints for Table Domain
--------------------------------------------------------
  ALTER TABLE "CONFIGUSER"."DOMAIN" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."DOMAIN" MODIFY ("DOMAINNAME" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."DOMAIN" MODIFY ("CREATETIME" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."DOMAIN" ADD CONSTRAINT DOMAIN UNIQUE ("DOMAINNAME") ENABLE;
  
--------------------------------------------------------
--  DDL for Index fk_domainId
--------------------------------------------------------
  CREATE UNIQUE INDEX "CONFIGUSER"."fk_domainId" ON "CONFIGUSER"."ADDRESS" ("DOMAINID" ASC);
    
--------------------------------------------------------
--  Constraints for Table ADDRESS
--------------------------------------------------------
  ALTER TABLE "CONFIGUSER"."ADDRESS" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."ADDRESS" MODIFY ("CREATETIME" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."ADDRESS" ADD CONSTRAINT FK_DOMAINID FOREIGN KEY (DOMAINID) REFERENCES "CONFIGUSER"."DOMAIN" (ID) ENABLE;

--------------------------------------------------------
--  Constraints for Table ANCHOR
-------------------------------------------------------- 
  ALTER TABLE "CONFIGUSER"."ANCHOR" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."ANCHOR" MODIFY ("CERTIFICATEID" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."ANCHOR" MODIFY ("OWNER" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."ANCHOR" MODIFY ("THUMBPRINT" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."ANCHOR" MODIFY ("CERTIFICATEDATA" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."ANCHOR" MODIFY ("VALIDSTARTDATE" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."ANCHOR" MODIFY ("VALIDENDDATE" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."ANCHOR" MODIFY ("INCOMING"  NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."ANCHOR" MODIFY ("OUTGOING" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."ANCHOR" MODIFY ("CREATETIME" NOT NULL ENABLE);
  
--------------------------------------------------------
--  Constraints for Table CERTIFICATE
--------------------------------------------------------
  ALTER TABLE "CONFIGUSER"."CERTIFICATE" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."CERTIFICATE" MODIFY ("OWNER" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTIFICATE" MODIFY ("THUMBPRINT" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTIFICATE" MODIFY ("VALIDSTARTDATE" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTIFICATE" MODIFY ("VALIDENDDATE" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTIFICATE" MODIFY ("CERTIFICATEDATA" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTIFICATE" MODIFY ("PRIVATEKEY" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTIFICATE" MODIFY ("STATUS" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTIFICATE" MODIFY ("CREATETIME" NOT NULL ENABLE);
  
-------------------------------------------------------------
--  Constraints for Table SETTING
-------------------------------------------------------------
  ALTER TABLE "CONFIGUSER"."SETTING" ADD PRIMARY KEY ("ID") ENABLE;
  
--------------------------------------------------------
--  Constraints for Table TRUSTBUNDLE
--------------------------------------------------------
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLE" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLE" MODIFY ("BUNDLENAME" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLE" MODIFY ("BUNDLEURL" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLE" MODIFY ("BUNDLECHECKSUM" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLE" MODIFY ("CREATETIME" NOT NULL ENABLE);
  
--------------------------------------------------------
--  DDL for Index fk_trustbundleId
--------------------------------------------------------
  CREATE UNIQUE INDEX "CONFIGUSER"."fk_trustbundleId" ON "CONFIGUSER"."TRUSTBUNDLEANCHOR" ("TRUSTBUNDLEID" ASC);
  
--------------------------------------------------------
--  Constraints for Table TRUSTBUNDLEANCHOR
--------------------------------------------------------
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEANCHOR" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEANCHOR" MODIFY ("ANCHORDATA" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEANCHOR" MODIFY ("THUMBPRINT" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEANCHOR" MODIFY ("VALIDSTARTDATE" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEANCHOR" MODIFY ("VALIDENDDATE" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEANCHOR" MODIFY ("TRUSTBUNDLEID" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEANCHOR" ADD CONSTRAINT fk_trustbundleId FOREIGN KEY (TRUSTBUNDLEID) REFERENCES "CONFIGUSER"."TRUSTBUNDLE"("ID") ENABLE;

--------------------------------------------------------
--  DDL for Index fk_domain_id
--------------------------------------------------------
  CREATE UNIQUE INDEX "CONFIGUSER"."fk_domain_id" ON "CONFIGUSER"."TRUSTBUNDLEDOMAINRELTN" ("DOMAIN_ID" ASC);  
  
--------------------------------------------------------
--  DDL for Index fk_trust_bundle_id
--------------------------------------------------------
  CREATE UNIQUE INDEX "CONFIGUSER"."fk_trust_bundle_id" ON "CONFIGUSER"."TRUSTBUNDLEDOMAINRELTN" ("TRUST_BUNDLE_ID" ASC);  
 
-----------------------------------------------------------
--  Constraints for Table TRUSTBUNDLEDOMAINRELTN
-----------------------------------------------------------
	
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEDOMAINRELTN" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEDOMAINRELTN" MODIFY ("INCOMING" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEDOMAINRELTN" MODIFY ("OUTGOING" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEDOMAINRELTN" MODIFY ("DOMAIN_ID" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEDOMAINRELTN" MODIFY ("TRUST_BUNDLE_ID" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEDOMAINRELTN" ADD CONSTRAINT fk_domain_id FOREIGN KEY (DOMAIN_ID) REFERENCES "CONFIGUSER"."DOMAIN"("ID");
  ALTER TABLE "CONFIGUSER"."TRUSTBUNDLEDOMAINRELTN" ADD CONSTRAINT fk_trust_bundle_id FOREIGN KEY (TRUST_BUNDLE_ID) REFERENCES "CONFIGUSER"."TRUSTBUNDLE"("ID");
	
-----------------------------------------------------------
--  Constraints for Table CERTPOLICY
-----------------------------------------------------------	

  ALTER TABLE "CONFIGUSER"."CERTPOLICY" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."CERTPOLICY" MODIFY ("CREATETIME" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTPOLICY" MODIFY ("LEXICON" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTPOLICY" MODIFY ("POLICYDATA" NOT NULL ENABLE);
  
-----------------------------------------------------------
--  Constraints for Table CERTPOLICYGROUP
-----------------------------------------------------------	

  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUP" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUP" MODIFY ("CREATETIME" NOT NULL ENABLE);
  
  
----------------------------------------------------------
--  Constraints for Table CERTPOLICYGROUPDOMAINRELTN
-----------------------------------------------------------	
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUPDOMAINRELTN" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUPDOMAINRELTN" MODIFY ("POLICY_GROUP_ID" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUPDOMAINRELTN" ADD CONSTRAINT fk_cert_domain_id FOREIGN KEY ("DOMAIN_ID") REFERENCES "CONFIGUSER"."DOMAIN"("ID");
  
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUPDOMAINRELTN" ADD CONSTRAINT fk_cert_policy_group_id FOREIGN KEY ("POLICY_GROUP_ID") REFERENCES "CONFIGUSER"."CERTPOLICYGROUP"("ID");
  

-----------------------------------------------------------
--  Constraints for Table CERTPOLICYGROUPRELTN
-----------------------------------------------------------	
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUPRELTN" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUPRELTN" MODIFY ("POLICYUSE" NOT NULL ENABLE);
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUPRELTN" MODIFY ("CERTPOLICYID" NOT NULL ENABLE);  
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUPRELTN" MODIFY ("CERTPOLICYGROUPID" NOT NULL ENABLE); 
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUPRELTN" ADD CONSTRAINT fk_cert_policy_id FOREIGN KEY ("CERTPOLICYID") REFERENCES "CONFIGUSER"."CERTPOLICY"("ID");
  ALTER TABLE "CONFIGUSER"."CERTPOLICYGROUPRELTN" ADD CONSTRAINT fk_cert_policy_reltn_group_id FOREIGN KEY ("CERTPOLICYGROUPID") REFERENCES "CONFIGUSER"."CERTPOLICYGROUP"("ID");
  
-----------------------------------------------------------
--  Constraints for Table DNSRECORD
-----------------------------------------------------------
  ALTER TABLE "CONFIGUSER"."DNSRECORD" ADD PRIMARY KEY ("ID") ENABLE;
  
-----------------------------------------------------------
--  Constraints for Table USERROLE
-----------------------------------------------------------  
  ALTER TABLE "NHINCUSER"."USERROLE" ADD PRIMARY KEY ("ROLEID") ENABLE;
  ALTER TABLE "NHINCUSER"."USERROLE" ADD CONSTRAINT USERROLE UNIQUE ("ROLENAME") ENABLE;
  
-----------------------------------------------------------
--  Constraints for Table PAGEPREFERENCE
----------------------------------------------------------- 
  ALTER TABLE "NHINCUSER"."PAGEPREFERENCE" ADD PRIMARY KEY ("PREFID") ENABLE;
  ALTER TABLE "NHINCUSER"."PAGEPREFERENCE" ADD CONSTRAINT fk_role_pref FOREIGN KEY (prefRoleId) REFERENCES "NHINCUSER"."USERROLE"("ROLEID");
-----------------------------------------------------------
--  Constraints for Table MONITOREDMESSAGE
----------------------------------------------------------- 	
	
  ALTER TABLE "NHINCUSER"."MONITOREDMESSAGE" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "NHINCUSER"."MONITOREDMESSAGE" MODIFY ("ID" NOT NULL ENABLE);
 
----------------------------------------------------------- 
--  Constraints for Table MONITOREDMESSAGENOTIFICATION
----------------------------------------------------------- 
  ALTER TABLE "NHINCUSER"."MONITOREDMESSAGENOTIFICATION" MODIFY ("STATUS" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."MONITOREDMESSAGENOTIFICATION" ADD CONSTRAINT fk_monitoredmessageId FOREIGN KEY (MONITOREDMESSAGEID) REFERENCES "NHINCUSER"."MONITOREDMESSAGE"("ID") ;
		
--------------------------------------------------------
--  DDL for Index MESSAGEID_IDX
--------------------------------------------------------
  CREATE UNIQUE INDEX "NHINCUSER"."MESSAGEID_IDX" ON "NHINCUSER"."TRANSACTIONREPOSITORY" ("MESSAGEID");
  
--------------------------------------------------------
--  Constraints for Table CORRELATEDIDENTIFIERS
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."CORRELATEDIDENTIFIERS" ADD PRIMARY KEY ("CORRELATIONID") ENABLE;
  ALTER TABLE "NHINCUSER"."CORRELATEDIDENTIFIERS" MODIFY ("CORRELATEDPATIENTID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."CORRELATEDIDENTIFIERS" MODIFY ("CORRELATEDPATIENTASSIGNAUTHID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."CORRELATEDIDENTIFIERS" MODIFY ("PATIENTID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."CORRELATEDIDENTIFIERS" MODIFY ("PATIENTASSIGNINGAUTHORITYID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."CORRELATEDIDENTIFIERS" MODIFY ("CORRELATIONID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table AUDITREPOSITORY
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."AUDITREPOSITORY" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "NHINCUSER"."AUDITREPOSITORY" MODIFY ("MESSAGETYPE" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."AUDITREPOSITORY" MODIFY ("EVENTID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."AUDITREPOSITORY" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PATIENT
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."PATIENT" ADD PRIMARY KEY ("PATIENTID") ENABLE;
  ALTER TABLE "NHINCUSER"."PATIENT" MODIFY ("PATIENTID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ADDRESS
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."ADDRESS" ADD PRIMARY KEY ("ADDRESSID") ENABLE;
  ALTER TABLE "NHINCUSER"."ADDRESS" MODIFY ("PATIENTID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."ADDRESS" MODIFY ("ADDRESSID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table IDENTIFIER
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."IDENTIFIER" ADD PRIMARY KEY ("IDENTIFIERID") ENABLE;
  ALTER TABLE "NHINCUSER"."IDENTIFIER" MODIFY ("PATIENTID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."IDENTIFIER" MODIFY ("IDENTIFIERID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EVENTCODE
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."EVENTCODE" ADD PRIMARY KEY ("EVENTCODEID") ENABLE;
  ALTER TABLE "NHINCUSER"."EVENTCODE" MODIFY ("DOCUMENTID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."EVENTCODE" MODIFY ("EVENTCODEID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PERSONNAME
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."PERSONNAME" ADD PRIMARY KEY ("PERSONNAMEID") ENABLE;
  ALTER TABLE "NHINCUSER"."PERSONNAME" MODIFY ("PATIENTID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."PERSONNAME" MODIFY ("PERSONNAMEID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EVENT
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."EVENT" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "NHINCUSER"."EVENT" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."EVENT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TRANSFER_DATA
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."TRANSFER_DATA" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "NHINCUSER"."TRANSFER_DATA" MODIFY ("TRANSFERSTATE" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."TRANSFER_DATA" MODIFY ("REQUESTKEYGUID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."TRANSFER_DATA" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table DOCUMENT
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."DOCUMENT" ADD PRIMARY KEY ("DOCUMENTID") ENABLE;
  ALTER TABLE "NHINCUSER"."DOCUMENT" MODIFY ("ONDEMAND" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."DOCUMENT" MODIFY ("PERSISTENT" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."DOCUMENT" MODIFY ("DOCUMENTUNIQUEID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."DOCUMENT" MODIFY ("DOCUMENTID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table TRANSACTIONREPOSITORY
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."TRANSACTIONREPOSITORY" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "NHINCUSER"."TRANSACTIONREPOSITORY" MODIFY ("MESSAGEID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."TRANSACTIONREPOSITORY" MODIFY ("TRANSACTIONID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."TRANSACTIONREPOSITORY" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table AA_TO_HOME_COMMUNITY_MAPPING
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."AA_TO_HOME_COMMUNITY_MAPPING" ADD PRIMARY KEY ("ID", "ASSIGNINGAUTHORITYID") ENABLE;
  ALTER TABLE "NHINCUSER"."AA_TO_HOME_COMMUNITY_MAPPING" MODIFY ("HOMECOMMUNITYID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."AA_TO_HOME_COMMUNITY_MAPPING" MODIFY ("ASSIGNINGAUTHORITYID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."AA_TO_HOME_COMMUNITY_MAPPING" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PDDEFERREDCORRELATION
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."PDDEFERREDCORRELATION" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "NHINCUSER"."PDDEFERREDCORRELATION" MODIFY ("CREATIONTIME" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."PDDEFERREDCORRELATION" MODIFY ("PATIENTID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."PDDEFERREDCORRELATION" MODIFY ("ASSIGNINGAUTHORITYID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."PDDEFERREDCORRELATION" MODIFY ("MESSAGEID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."PDDEFERREDCORRELATION" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PHONENUMBER
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."PHONENUMBER" ADD PRIMARY KEY ("PHONENUMBERID") ENABLE;
  ALTER TABLE "NHINCUSER"."PHONENUMBER" MODIFY ("PATIENTID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."PHONENUMBER" MODIFY ("PHONENUMBERID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ASYNCMSGREPO
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."ASYNCMSGREPO" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "NHINCUSER"."ASYNCMSGREPO" MODIFY ("CREATIONTIME" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."ASYNCMSGREPO" MODIFY ("MESSAGEID" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."ASYNCMSGREPO" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table USERLOGIN
--------------------------------------------------------

  ALTER TABLE "NHINCUSER"."USERLOGIN" ADD PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "NHINCUSER"."USERLOGIN" MODIFY ("SALT" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."USERLOGIN" MODIFY ("SHA1" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."USERLOGIN" MODIFY ("USERNAME" NOT NULL ENABLE);
  ALTER TABLE "NHINCUSER"."PAGEPREFERENCE" ADD CONSTRAINT fk_role_user FOREIGN KEY (USERROLE) REFERENCES "NHINCUSER"."USERROLE"("ROLEID");

  INSERT INTO NHINCUSER.USERLOGIN
    (ID, SALT, SHA1, USERNAME)
  VALUES
    (1, 'ABCD', 'TxMu4SPUdek0XU5NovS9U2llt3Q=', 'CONNECTAdmin');
	
  INSERT INTO NHINCUSER.USERROLE
    (ROLEID, ROLENAME)
  VALUES
    (1, 'ADMIN');
	
  INSERT INTO NHINCUSER.USERROLE
    (ROLEID, ROLENAME)
  VALUES
    (2, 'SUPER USER');
    
  INSERT INTO NHINCUSER.USERROLE
    (ROLEID, ROLENAME)
  VALUES
    (3, 'USER');


 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID, PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (1, 'status.xhtml', 'Status', 0, 1);
 
 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID, PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (2,'status.xhtml', 'Status', 0, 2);

 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID,PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (3,'status.xhtml', 'Status', 0, 3);
    
 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID, PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (4, 'acctmanage.xhtml', 'Account Management', 0, 1);
    
 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID, PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (5, 'acctmanage.xhtml', 'Account Management', -1, 2);
    
 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID, PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (6, 'acctmanage.xhtml', 'Account Management', -1, 3);

 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID, PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (7, 'manageRole.xhtml', 'Manage Role', 0, 1);

 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID,PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (8, 'manageRole.xhtml', 'Manage Role', -1, 2);

 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID, PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (9, 'manageRole.xhtml', 'Manage Role', -1, 3);

 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID, PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (10, 'direct.xhtml', 'Direct Config', 0, 1);

 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID, PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (11, 'direct.xhtml', 'Direct Config', 0, 2);

 INSERT INTO NHINCUSER.PAGEPREFERENCE
   (PREFID, PAGENAME, PAGEDESC, ACCESSPAGE, PREFROLEID)
   VALUES
    (12, 'direct.xhtml', 'Direct Config', 0, 3);
 COMMIT;