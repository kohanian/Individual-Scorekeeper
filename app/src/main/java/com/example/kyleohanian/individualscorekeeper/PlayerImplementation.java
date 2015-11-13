package com.example.kyleohanian.individualscorekeeper;


 // Created by kyleohanian on 10/20/15.


public class PlayerImplementation {

    // Declaration of Player's Attributes
    String fullname;
    int age, g, ab, r, h, d, t, hr, rbi, sb, cs,
            bb, k, gidp, hbp, sh, sf, ibb;

    //Batter Class Constructor

        /*
         * This class actually takes two constructors.
         * One will take a String, and the array of
         * strings, and the other will take and
         * initialize all player attributes.
         */
    public PlayerImplementation(String fullname, int g, int ab, int r, int h, int d, int t, int hr,
                                int rbi, int sb, int cs, int bb, int k, int gidp, int hbp, int sh,
                                int sf, int ibb) {
        this.fullname = fullname;
        this.g = g;
        this.ab = ab;
        this.r = r;
        this.h = h;
        this.d = d;
        this.t = t;
        this.hr = hr;
        this.rbi = rbi;
        this.sb = sb;
        this.cs = cs;
        this.bb = bb;
        this.k = k;
        this.gidp = gidp;
        this.hbp = hbp;
        this.sh = sh;
        this.sf = sf;
        this.ibb = ibb;
    }

        /*
         * These are the public accessor methods.
         * All are implemented for all of the
         * different attributes in the Batter Class.
         */

    public String getFullName() {
        return fullname;
    }

    public int getGames() {
        return g;
    }

    public int getAtBats() {
        return ab;
    }

    public int getRuns() {
        return r;
    }

    public int getHits() {
        return h;
    }

    public int getDoubles() {
        return d;
    }

    public int getTriples() {
        return t;
    }

    public int getHomeRuns() {
        return hr;
    }

    public int getRunsBattedIn() {
        return rbi;
    }

    public int getStolenBases() {
        return sb;
    }

    public int getCaughtStealing() {
        return cs;
    }

    public int getWalks() {
        return bb;
    }

    public int getStrikeouts() {
        return k;
    }

    public int getGroundedIntoDoublePlay() {
        return gidp;
    }

    public int getHitByPitch() {
        return hbp;
    }

    public int getSacraficeHits() {
        return sh;
    }

    public int getSacraficeFlies() {
        return sf;
    }

    public int getIntentionalWalks() {
        return ibb;
    }

        /*
         * After that, these methods calculate
         * certain stats based off the
         * attribute stats above. They also return
         * the specified type.
         * Ex: Batting Average is calculated by taking
         * hits and at-bats. So those variables are
         * included in the method.
         */

    public int getSingles() {
        return h-(d+t+hr);
    }
    public int getNonIntentionalWalks() {
        return bb-ibb;
    }
    public int getReachedOnBaseByError() {
        return getPlateAppearances()-bb-h-hbp;
    }
    public double getBattingAverage() {
        return (double)h/ab;
    }
    public double getOnBasePercentage() {
        return (h+bb+hbp)/(double)getPlateAppearances();
    }
    public int getPlateAppearances() {
        return ab+bb+hbp+sf+sh;
    }
    public double getSluggingPercentage() {
        return (double)getTotalBases()/ab;
    }
    public double getOnBasePlusSlugging() {
        return getOnBasePercentage()+getSluggingPercentage();
    }
    public int getTotalBases() {
        return (getSingles()+2*d+3*t+4*hr);
    }
    public int getExtraBaseHits() {
        return d+t+hr;
    }
    public int getOuts() {
        return (ab-h)+gidp+sf+sh+cs;
    }
    public double getExtrapolatedRuns() {
        return (double).5*getSingles()+.72*d+1.04*t+1.44*hr+.34*(hbp+getTotalBases()-ibb)+.25*ibb+.18*sb-.32*cs-.09*(ab-h-k)-.098*k-.37*gidp+.37*sf+.04*sh;
    }
    public double getExtrapolatedRunsReduced() {
        return (double).5*getSingles()+.72*d+1.04*t+1.44*hr+.33*(hbp+getTotalBases())+.18*sb-.32*cs-.098*(ab-h);
    }
    public double getExtrapolatedRunsBasic() {
        return (double).5*getSingles()+.72*d+1.04*t+1.44*hr+.34*getTotalBases()+.18*sb-.32*cs-.096*(ab-h);
    }
    public double getRunsCreated() {
        return (((double)h+bb-cs+hbp-gidp)*(getTotalBases()+.26*(bb-ibb+hbp))+.52*(sh+sf+sb))/(ab+bb+hbp+sh+sf);
    }
    public double getWeightedOnBaseAverage() {
        return ((double).72*bb+.75*hbp+.9*getSingles()+.92*r+1.24*d+1.56*t+1.95*hr)/getPlateAppearances();
    }
    public double getSecondaryAverage() {
        return ((double)bb+getTotalBases()-h+sb-cs)/ab;
    }
    public double getWeightedRunsAboveAverage() {
        return ((getWeightedOnBaseAverage()-.32)/1.25)*(ab+bb+hbp+sh+sf);
    }
    public double getBattingAverageBallsInPlay() {
        return (h-hr)/(ab-k-h+sf);
    }
    public double getEquivalentAverage() {
        return ((double)h+getTotalBases()+1.5*(bb+hbp)+sb+sh+sf)/(ab+bb+hbp+sh+sf+cs+(sb/3));
    }
    public double getTotalAverage() {
        return ((double)getTotalBases()+hbp+bb+sb)/(ab-h+cs+gidp);
    }
    public double getPowerSpeedNumber() {
        return (2*(double)hr*sb)/(hr+sb);
    }
    public double getIsolatedPower() {
        return getSluggingPercentage()-getBattingAverage();
    }

    public double getHomeRunPlateAppearanceRatio() {
        return (double)hr/getPlateAppearances();
    }

    public double getStrikeoutPlateAppearanceRatio() {
        return (double)k/getPlateAppearances();
    }

    public double getWalkPlateAppearanceRatio() {
        return (double)bb/getPlateAppearances();
    }

    public double getExtraBaseHitPlateAppearanceRatio() {
        return (double)getExtraBaseHits()/getPlateAppearances();
    }

    public double getExtraBaseHitHitsRatio() {
        return (double)getExtraBaseHits()/h;
    }

    public double getStrikeoutWalkRatio() {
        return (double)k/bb;
    }

    public double getAtBatStrikeoutRatio() {
        return (double)ab/k;
    }

    public double getAtBatHomeRunRatio() {
        return (double)ab/hr;
    }

    public double getAtBatRunsBattedInRatio() {
        return (double)ab/rbi;
    }

    public double getDoubleHitRatio() {
        return (double)d/h;
    }

    public double getTripleHitRatio() {
        return (double)t/h;
    }

    public double getHomeRunHitRatio() {
        return (double)hr/h;
    }

    public double getBallsInPlayPlateAppearanceRatio() {
        return (double)(ab-k-hr+sf)/getPlateAppearances();
    }

        /*
         * This method returns a stat that
         * will be specified in the argument
         * for an attribute stat.
         */

}
