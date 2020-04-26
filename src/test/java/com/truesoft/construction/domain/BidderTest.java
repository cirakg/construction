package com.truesoft.construction.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.truesoft.construction.web.rest.TestUtil;

public class BidderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bidder.class);
        Bidder bidder1 = new Bidder();
        bidder1.setId(1L);
        Bidder bidder2 = new Bidder();
        bidder2.setId(bidder1.getId());
        assertThat(bidder1).isEqualTo(bidder2);
        bidder2.setId(2L);
        assertThat(bidder1).isNotEqualTo(bidder2);
        bidder1.setId(null);
        assertThat(bidder1).isNotEqualTo(bidder2);
    }
}
