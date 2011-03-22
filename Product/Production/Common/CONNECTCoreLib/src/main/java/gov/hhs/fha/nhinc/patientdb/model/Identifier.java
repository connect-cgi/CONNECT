/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2011(Year date of delivery) United States Government, as represented by the Secretary of Health and Human Services.  All rights reserved.
 *
 */
package gov.hhs.fha.nhinc.patientdb.model;

import java.io.Serializable;

/**
 *
 * @author richard.ettema
 */
public class Identifier implements Serializable {

    /**
     * Attribute identifierId.
     */
    private Long identifierId;

    /**
     * Attribute patientId.
     */
    private Long patientId;

    /**
     * Attribute id.
     */
    private String id;

    /**
     * Attribute organizationId.
     */
    private String organizationId;


    /**
     * @return identifierId
     */
    public Long getIdentifierId() {
        return identifierId;
    }

    /**
     * @param identifierId new value for identifierId
     */
    public void setIdentifierId(Long identifierId) {
        this.identifierId = identifierId;
    }

    /**
     * @return patientId
     */
    public Long getPatientId() {
        return patientId;
    }

    /**
     * @param patientId new value for patientId
     */
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id new value for id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return organizationId
     */
    public String getOrganizationId() {
        return organizationId;
    }

    /**
     * @param organizationId new value for organizationId
     */
    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

}