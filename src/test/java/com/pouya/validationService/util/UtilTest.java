package com.pouya.validationService.util;


import com.pouya.validationService.exception.InvalidPhoneNumberException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilTest {

    private com.pouya.validationService.util.Util util;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        util = new com.pouya.validationService.util.Util();
    }

    @Test
    public void handleGmailAndHotmail_shouldRemoveAnythingAfterPlus() {
        String input = "abc+100@gmail.com";

        String returnValue = util.handleGmailAndHotmail(input);

        assertThat(returnValue).isEqualTo("abc@gmail.com");
    }

    @Test
    public void handleGmailAndHotmail_shouldRemoveAllTheDots() {
        String input = "john.doe.jr+100@gmail.com";

        String returnValue = util.handleGmailAndHotmail(input);

        assertThat(returnValue).isEqualTo("johndoejr@gmail.com");
    }

    @Test
    public void handleGmailAndHotmail_shouldLowerCaseEverything() {
        String input = "john.DOE.jr@gmail.com";

        String returnValue = util.handleGmailAndHotmail(input);

        assertThat(returnValue).isEqualTo("johndoejr@gmail.com");
    }

    @Test
    public void handlePhoneNumberForInternational_shouldRemoveEverythingBesideNumeric() {
        String input = "+1(310)499-      4127";

        String returnValue = util.handlePhoneNumberForInternational(input);

        assertThat(returnValue).isEqualTo("13104994127");
    }

    @Test
    public void handlePhoneNumberForUSOrCanada_shouldAddOneForUsaAndCanadaPhoneNumber() {
        String input = "(310)499-4127";

        String returnValue = util.handlePhoneNumberForUSOrCanada(input, "us");

        assertThat(returnValue).isEqualTo("13104994127");
    }

    @Test
    public void handlePhoneNumberForUSOrCanada_ifItStartsWithZero_shouldAllGetsRemoved() {
        String input = "+001(310)499-4127";

        String returnValue = util.handlePhoneNumberForUSOrCanada(input, "us");

        assertThat(returnValue).isEqualTo("13104994127");

    }

    @Test
    public void handlePhoneNumberForUSOrCanada_ifItStartsWithOne_shouldThrowAnException() {
        exception.expect(InvalidPhoneNumberException.class);
        exception.expectMessage("Invalid phone number for US");

        String input = "(110)499-4127";

        util.handlePhoneNumberForUSOrCanada(input, "us");

    }

    @Test
    public void handlePhoneNumberForUSOrCanada_ifItHasMoreNumberThanAllowed_shouldThrowAnException() {
        exception.expect(InvalidPhoneNumberException.class);
        exception.expectMessage("Invalid phone number for US");

        String input = "(101) 456-8855999";

        util.handlePhoneNumberForUSOrCanada(input, "us");


    }
}