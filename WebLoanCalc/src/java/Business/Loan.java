//Kbowen
//Java Programming III (IS-288-210) Credit Spring 2021   


package business;

public class Loan 
{
    private double principal, rate, mopmt;
    private int term;
    private double[] bbal, ichrg, ebal;
    private boolean built;
    private String emsg;
    
    public Loan() //constructor
    {
        this.principal = 0;
        this.rate = 0;
        this.term = 0;
        this.built = false;
        this.emsg = "";
    }
    
    public Loan(double p,double r,int t) 
    {
	this.principal = p;
	this.rate = r;
	this.term = t;
        //validate input data here
	this.mopmt = calcMoPmt();
        
        if (isValid())
        {
            buildLoan(); 
        }
    }
    
    private boolean isValid()
    {
        this.emsg = ""; //start empty or cleared
        boolean valid = true;
        if (this.principal <= 0) 
        {
            valid = false;
            this.emsg += "Principal must be positive. ";
        }
        if (this.rate <= 0) 
        {
            valid = false;
            this.emsg += "Rate must be positive. ";
        }
        if (this.term <= 0) 
        {
            valid = false;
            this.emsg += "Term must be positive. ";
        }
        return valid;
    }
    
    public String getErrorMsg() 
    {
        return this.emsg;
    }
    
    protected void setErrorMsg(String msg)
    {
        this.emsg = msg;        
    }
    
    public void setPrincipal(double p) 
    {
        this.principal = p;
        built = false;
    }
    
    public double getPrincipal() 
    {
        return this.principal;
    }
    
    public void setRate(double r) 
    {
        this.rate = r;
        built = false;
    }
    
    public double getRate() 
    {
        return this.rate;
    }
    
    public void setTerm(int t) 
    {
        this.term = t;
        built = false;
    }
    
    public int getTerm() 
    {
        return this.term;
    }
    
    public double getMonthlyPayment() 
    {
	if (!built) 
        {
            this.mopmt = calcMoPmt();
            buildLoan();
	}
	return this.mopmt;
    }
    
    public double getBegBal(int m) 
    {
        if (!built) 
        {
            buildLoan();
        }
        return this.bbal[m-1];
    }
    
    public double getIntCharged(int m) 
    {
        if (!built) 
        {
            buildLoan();
        }
        return this.ichrg[m-1];
    }
    public double getEndBal(int m) 
    {
        if (!built) 
        {
            buildLoan();
        }
        return this.ebal[m-1];
    }
    
    
    private double calcMoPmt() 
    {
	double mp, mrate, denom;
	mrate = this.rate / 12.0;
	denom = Math.pow(1 + mrate, term)-1;
	mp = (mrate + (mrate/denom))*principal;
	return mp;
    }
    
    private void buildLoan() 
    {
        bbal = new double[term];
        ichrg = new double[term];
        ebal = new double[term];

        bbal[0] = this.principal;


        for (int i=0;i < this.term; i++) 
        {
            if (i > 0) 
            {
                bbal[i] = ebal[i-1];
            }
            ichrg[i] = bbal[i]*(this.rate/12.0);
            ebal[i] = bbal[i] + ichrg[i] - this.mopmt;
        }
        built = true;
        return;
    }
}
