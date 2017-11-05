package com.alebit.jelsn.songname;

public class ElsSong implements Song {
    protected String songName = "";
    protected String folder = "";
    protected boolean security;
    protected String model = "";
    protected boolean partUk;
    protected boolean partLk;
    protected boolean partPk;
    protected boolean partLead;
    protected boolean partKbp;
    protected boolean partCtrl;
    protected boolean partXg;
    protected String midFile = "";
    protected String[] blkFile = new String[999];
    protected String secFile = "";

    public ElsSong() {
        for (int i = 0; i < 999; i++) {
            blkFile[i] = "";
        }
    }

    @Override
    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Override
    public String getSongName() {
        return songName;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFolder() {
        return folder;
    }

    public void setSecurity(boolean security) {
        this.security = security;
    }

    public boolean getSecurity() {
        return security;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public void setPartUk(boolean partUk) {
        this.partUk = partUk;
    }

    public boolean getPartUk() {
        return partUk;
    }

    public void setPartLk(boolean partLk) {
        this.partLk = partLk;
    }

    public boolean getPartLk() {
        return partLk;
    }

    public void setPartPk(boolean partPk) {
        this.partPk = partPk;
    }

    public boolean getPartPk() {
        return partPk;
    }

    public void setPartLead(boolean partLead) {
        this.partLead = partLead;
    }

    public boolean getPartLead() {
        return partLead;
    }

    public void setPartKbp(boolean partKbp) {
        this.partKbp = partKbp;
    }

    public boolean getPartKbp() {
        return partKbp;
    }

    public void setPartCtrl(boolean partCtrl) {
        this.partCtrl = partCtrl;
    }

    public boolean getPartCtrl() {
        return partCtrl;
    }

    public void setPartXg(boolean partXg) {
        this.partXg = partXg;
    }

    public boolean getPartXg() {
        return partXg;
    }

    public void setMidFile(String midFile) {
        this.midFile = midFile;
    }

    public String getMidFile() {
        return midFile;
    }

    public void setBlkFile(int index, String filename) {
        blkFile[index-1] = filename;
    }

    public String getBlkFile(int index) {
        return blkFile[index - 1];
    }

    public void setSecFile(String secFile) {
        this.secFile = secFile;
    }

    public String getSecFile() {
        return secFile;
    }
}
