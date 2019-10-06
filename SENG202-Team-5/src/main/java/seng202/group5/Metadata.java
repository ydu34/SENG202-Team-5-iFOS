package seng202.group5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Metadata {
    private String saveFileLocation = System.getProperty("user.dir");
    private boolean autosaveEnabled = true;
    private boolean autoloadEnabled = true;

    public Metadata() {
    }

    public boolean isAutosaveEnabled() {
        return autosaveEnabled;
    }

    public void setAutosaveEnabled(boolean autosaveEnabled) {
        this.autosaveEnabled = autosaveEnabled;
    }

    public boolean isAutoloadEnabled() {
        return autoloadEnabled;
    }

    public void setAutoloadEnabled(boolean autoloadEnabled) {
        this.autoloadEnabled = autoloadEnabled;
    }

    public String getSaveFileLocation() {
        return saveFileLocation;
    }

    public void setSaveFileLocation(String saveFileLocation) {
        this.saveFileLocation = saveFileLocation;
    }
}
