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
public class OrderBuilder implements ApplicationContextAware {

    private static final Logger LOGGER = LogManager.getLogger(OrderBuilder.class.getName());
    private ApplicationContext applicationContext;
    private OrdersPack ordersPack;

    private String orderId;
    private String amount;
    private String currency;
    private String comment;
    private String fileName;
    private int lineIndex;

    @Autowired
    public void setOrdersPack(OrdersPack ordersPack){
        this.ordersPack = ordersPack;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void buildOrder() {
        Order order = applicationContext.getBean("order", Order.class);
        order.setResult("OK");
        order.setOrderId(Integer.parseInt(orderId));
        parseAmount(order, amount);
        parseCurrency(order, currency);
        order.setComment(comment);
        order.setFileName(fileName);
        order.setLine(lineIndex);
        ordersPack.addOrder(order);
    }


    private void parseAmount(Order order, String cell) {
        try {
            order.setAmount(Double.parseDouble(cell));
        }catch (NumberFormatException ex){
            order.setResult("ERROR: the amount is not readable");
            LOGGER.info("amount is not readable in order id: "+order.getOrderId()+" from file: "+order.getFileName());
        }
    }

    private void parseCurrency(Order order, String cell) {
        try{
            order.setCurrency(Currency.getInstance(cell));
        }catch (IllegalArgumentException ex){
            order.setResult("ERROR: the currency not defined");
            LOGGER.info("currency not defined in order id:"+order.getOrderId()+" from file: "+order.getFileName());
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }
}
