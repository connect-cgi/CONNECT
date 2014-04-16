/*
 Copyright (c) 2010, Direct Project
 All rights reserved.

 Authors:
    Greg Meyer     gm2552@cerner.com

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
Neither the name of The Direct Project (directproject.org) nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package gov.hhs.fha.nhinc.directconfig.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The JPA settings class.  This tables holds various configuration settings such as how the configuration service should behave or settings
 * for a gateway.  This structure is made up of simple name value pairs.
 */
@Entity
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    private String name;

    @Column(length = 4096)
    private String value;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updateTime;

    /**
     * Get the name of the setting.
     *
     * @return the name of the setting.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the setting.
     *
     * @param name
     *            The name of setting.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the value of the setting.
     *
     * @return the value of the setting.
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the name of the setting.
     *
     * @param name
     *            The value of setting.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the value of id.
     *
     * @return the value of id.
     */
    public long getId() {
        return id;
    }

    /**
     * Set the value of id.
     *
     * @param id
     *            The value of id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get the value of createTime.
     *
     * @return the value of createTime.
     */
    public Calendar getCreateTime() {
        return createTime;
    }

    /**
     * Set the value of createTime.
     *
     * @param timestamp
     *            The value of createTime.
     */
    public void setCreateTime(Calendar timestamp) {
        createTime = timestamp;
    }

    /**
     * Get the value of updateTime.
     *
     * @return the value of updateTime.
     */
    public Calendar getUpdateTime() {
        return updateTime;
    }

    /**
     * Set the value of updateTime.
     *
     * @param timestamp
     *            The value of updateTime.
     */
    public void setUpdateTime(Calendar timestamp) {
        updateTime = timestamp;
    }
}
