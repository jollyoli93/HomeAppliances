package appliances;

/**
 * @author 24862664
 * Abstract class representing a general Appliance.
 * Provides common properties and methods for all appliances.
 */
public abstract class Appliance {

    /** The unique identifier of the appliance. */
    protected int id = 0;

    /** The Stock Keeping Unit (SKU) of the appliance. */
    protected String sku;

    /** A brief description of the appliance. */
    protected String description;

    /** The category to which the appliance belongs. */
    protected String category;

    /** The price of the appliance. */
    protected double price;

    /**
     * Gets the ID of the appliance.
     *
     * @return the ID of the appliance
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the ID of the appliance.
     *
     * @param id the new ID of the appliance
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the SKU of the appliance.
     *
     * @return the SKU of the appliance
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * Gets the description of the appliance.
     *
     * @return the description of the appliance
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the category of the appliance.
     *
     * @return the category of the appliance
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Gets the price of the appliance.
     *
     * @return the price of the appliance
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Updates the price of the appliance.
     *
     * @param updatedPrice the new price of the appliance
     */
    public void setPrice(double updatedPrice) {
        this.price = updatedPrice;
    }

    /**
     * Provides details of the appliance in a textual format.
     *
     * @return a string containing details about the appliance
     */
    public String getDetails() {
        return "Description: " + description + ", Category: " + category + ", Price: " + price + " ID:" + id + ", SKU: " + sku;
    }

    /**
     * Provides details of the appliance in an HTML table row format.
     *
     * @return a string representing the appliance as an HTML table row
     */
    public String toHTMLString() {
        return "<tr><td>" + id + "</td><td>" + description + "</td><td>" + category + "</td><td>" + price + "</td><td>" + sku + "</td></tr>";
    }
}

/**
 * Create a Basic TV Appliance.
 */
class BasicTVAppliance extends Appliance {
    /**
     * Constructs a Basic TV Appliance with predefined attributes.
     */
    public BasicTVAppliance() {
        this.sku = "TV001";
        this.description = "Basic Television";
        this.category = "Entertainment";
        this.price = 100;
    }
}

/**
 * Create an LCD TV Appliance.
 */
class LCDTVAppliance extends Appliance {
    /**
     * Constructs an LCD TV Appliance with predefined attributes.
     */
    public LCDTVAppliance() {
        this.sku = "TV002";
        this.description = "LCD Television";
        this.category = "Entertainment";
        this.price = 300;
    }
}

/**
 * Create a Basic Washing Machine Appliance.
 */
class BasicWashingMachineAppliance extends Appliance {
    /**
     * Constructs a Basic Washing Machine Appliance with predefined attributes.
     */
    public BasicWashingMachineAppliance() {
        this.sku = "WM001";
        this.description = "Basic Washing Machine";
        this.category = "Home Cleaning";
        this.price = 300;
    }
}

/**
 * Create a Super Fast Washing Machine Appliance.
 */
class SuperFastWashingMachineAppliance extends Appliance {
    /**
     * Constructs a Super Fast Washing Machine Appliance with predefined attributes.
     */
    public SuperFastWashingMachineAppliance() {
        this.sku = "WM002";
        this.description = "Super Fast Washing Machine";
        this.category = "Home Cleaning";
        this.price = 1000;
    }
}
