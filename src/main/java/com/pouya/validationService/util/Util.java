package com.pouya.validationService.util;


import com.pouya.validationService.exception.InvalidPhoneNumberException;
import org.springframework.stereotype.Component;

@Component
public class Util {

    public String handleGmailAndHotmail(String input) {
        if (input.contains("+")) {
            input = input
                    .substring(0, input.indexOf("+"))
                    .concat(input.substring(input.indexOf("@"), input.length()));
        }

        if (input.substring(0, input.indexOf("@")).contains(".")) {
            input = input
                    .substring(0, input.indexOf("@"))
                    .replace(".", "")
                    .concat(input.substring(input.indexOf("@")));
        }

        return input.toLowerCase();
    }

    public String handlePhoneNumberForInternational(String input) {
        return input.replaceAll("[^0-9]", "");
    }

    public String handlePhoneNumberForUSOrCanada(String input, String countryCode) {
        String onlyNumericNumber = input.replaceAll("[^0-9]", "");

        while (onlyNumericNumber.startsWith("0")) {
            onlyNumericNumber = onlyNumericNumber.substring(1);
        }

        String response = onlyNumericNumber;

        if (onlyNumericNumber.length() == 10 && !onlyNumericNumber.startsWith("1")) {
            response = "1" + onlyNumericNumber;
        } else if ((onlyNumericNumber.length() == 10 && onlyNumericNumber.startsWith("1"))
                || onlyNumericNumber.length() != 11) {
            throw new InvalidPhoneNumberException("Invalid phone number for " + countryCode.toUpperCase());
        }

        return response;
    }
}
