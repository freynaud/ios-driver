/**
 * @module ios-driver
 */

UIAAlert.prototype.defaultButton_origin = UIAAlert.prototype.defaultButton;

/**
 * dismiss the alert.
 */
UIAAlert.prototype.dismiss = function () {
    var button = this.cancelButton();
    if (button.type() === "UIAElementNil") {
        log("cancel is not there,trying default.");
        button = this.defaultButton();
        if (button.type() === "UIAElementNil") {
            throw new UIAutomationException("this alert doesn't have the normal buttons.", 7);
        }
    } else {
        log("cancel found");
    }

    button.tap();
}

/**
 * accept the alert.
 */
UIAAlert.prototype.accept = function () {
    var button = this.defaultButton();
    if (button.type() === "UIAElementNil") {
        throw new UIAutomationException("this alert doesn't have a default normal buttons.", 7);
    }
    button.tap();
}

/**
 * send the value to the alert, assuming it has a text field.
 * @param value
 */
UIAAlert.prototype.sendKeys = function (value) {
    var criteria = {"OR": [
        {"l10n": "none", "expected": "UIATextField", "matching": "exact", "method": "type"},
        {"l10n": "none", "expected": "UIASecureTextField", "matching": "exact", "method": "type"}
    ]};
    try {
        this.element(-1, criteria);
    } catch (err) {
        throw new UIAutomationException("this alert doesn't accept inputs.", 12);
    }
    var app = UIAutomation.cache.get(1);
    var keyboard = app.keyboard();
    keyboard.typeString(value);

}

/**
 * return the default button, but also send an event that the alert disappeared.
 * @return {UIAButton} the default button for the alert.
 */
UIAAlert.prototype.defaultButton = function () {
    var res = this.defaultButton_origin();
    res.tap = function () {
        if (this.isVisible()) {
            var rect = this.rect();
            var x = rect.origin.x + (rect.size.width / 2);
            var y = rect.origin.y + (rect.size.height / 2);
            var point = {
                'x': Math.floor(x),
                'y': Math.floor(y)
            };
            UIATarget.localTarget().tap(point);
            UIAutomation.cache.clearAlert();
        } else {
            throw new UIAutomationException("element is not visible", 11);
        }
    }
    return res;
}
UIAAlert.prototype.cancelButton_origin = UIAAlert.prototype.cancelButton;

/**
 * return the cancel button, but also send an event that the alert disappeared.
 * @return {UIAButton} the cancel button for the alert.
 */
UIAAlert.prototype.cancelButton = function () {
    var res = this.cancelButton_origin();

    res.tap = function () {
        if (this.isVisible()) {
            var rect = this.rect();
            var x = rect.origin.x + (rect.size.width / 2);
            var y = rect.origin.y + (rect.size.height / 2);
            var point = {
                'x': Math.floor(x),
                'y': Math.floor(y)
            };
            UIATarget.localTarget().tap(point);
            UIAutomation.cache.clearAlert();
        } else {
            throw new UIAutomationException("element is not visible", 11);
        }
    }
    return res;
}