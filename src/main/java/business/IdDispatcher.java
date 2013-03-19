/**
 * 
 */
package business;

/**
 * @author alexander
 *
 */
public enum IdDispatcher {
	INSTANCE;
	
	private long orderId_ = 0;
	private long userId_ = 0;
	private long publisherId_ = 0;
	private long requestId = 0;
	
	public long getNewOrderId() {
		return orderId_++;
	}
	
	public long getNewUserId() {
		return userId_++;
	}
	
	public long getNewPublisherId() {
		return publisherId_++;
	}
	
	public long getNewRequestId() {
		return requestId++;
	}
}
