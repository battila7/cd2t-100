package hu.progtech.cd2t100.computation;

/**
 *	Enumeration of the the possible execution states of a {@code Node}
 *	object.
 */
public enum ExecutionState {
	/**
	 *	Idle state, the node's not executing any instructions.
	 */
	IDLE,

	/**
	 *	The node is running.
	 */
	RUN,

	/**
	 *	The node is performing a read operation.
	 */
	READ,

	/**
	 *	The node is performing a write operation.
	 */
	WRITE
}
