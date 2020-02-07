    package org.ysnam.householdAccounts.core.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
class ItemKey implements Serializable {

    Integer remainAmount;
    Long dateLong;
}
