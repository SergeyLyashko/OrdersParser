package orders;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Currency;

@Service("orderBuilder")
@Scope("prototype")
class OrderBuilderImpl implements OrderBuilder, ApplicationContextAware {

    private static final Logger LOGGER = LogManager.getLogger(OrderBuilderImpl.class.getName());
    private final StringBuffer errorString;
    private ApplicationContext applicationContext;
    private OrdersPack ordersPack;
    private String orderId;
    private String amount;
    private String currency;
    private String comment;
    private String fileName;
    private int lineIndex;

    OrderBuilderImpl() {
        errorString = new StringBuffer("ERROR: ");
    }

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    @Override
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    @Override
    public void buildOrder() {
        Order order = applicationContext.getBean("order", Order.class);
        order.setResult("OK");
        order.setOrderId(Integer.parseInt(orderId));
        order.setFileName(fileName);
        order.setComment(comment);
        order.setLine(lineIndex);
        setCheckedValue(order);
        ordersPack.addOrder(order);
    }

    private void setCheckedValue(Order order){
        double parseAmount = parseAmount(amount, order);
        Currency parseCurrency = parseCurrency(currency, order);
        if(parseAmount == -1 || parseCurrency == null){
            order.setResult(errorString.toString().trim());
        }
        if(parseAmount != -1){
            order.setAmount(parseAmount);
        }
        if(currency != null){
            order.setCurrency(parseCurrency);
        }
    }

    private double parseAmount(String cell, Order order) {
        try {
            return Double.parseDouble(cell);
        }catch (NumberFormatException ex){
            errorString.append("wrong amount value ");
            LOGGER.info("amount is not readable in order id: "+order.getOrderId()+" from file: "+order.getFileName());
        }
        return -1;
    }

    private Currency parseCurrency(String cell, Order order) {
        try{
            return Currency.getInstance(cell);
        }catch (IllegalArgumentException ex){
            errorString.append("wrong currency code ");
            LOGGER.info("currency not defined in order id:"+order.getOrderId()+" from file: "+order.getFileName());
        }
        return null;
    }
}
