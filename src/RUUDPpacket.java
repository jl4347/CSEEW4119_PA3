import java.nio.ByteBuffer;


public class RUUDPpacket {

	private byte[] outputPacket;
	private byte[] inputPacket;
	private byte[] payload;
	private int sourcePort;
	private int destPort;
	private int dataOffset;
	private int checksum;
	
	public final int INT_BYTE_LENGTH = 4;
	public final int DATA_OFFSET = 8;
	public final int CHECKSUM_INDEX = 12;
	public final int HEADER_LENGTH = 16;
	
	public RUUDPpacket() {
		
	}
	
	public void createPacket(byte[] payload, int sourcePort, int destPort) {
		this.payload = payload;
		outputPacket = new byte[HEADER_LENGTH + payload.length];
		
		byte[] sendPortByte = ByteBuffer.allocate(4).putInt(sourcePort).array();
		byte[] destPortByte = ByteBuffer.allocate(4).putInt(destPort).array();
		byte[] dataOffset = ByteBuffer.allocate(4).putInt(HEADER_LENGTH).array();
		
		int checksum = computeChecksum(sourcePort, destPort, HEADER_LENGTH, payload.length);
		byte[] checksumByte = ByteBuffer.allocate(4).putInt(checksum).array();
		
		System.arraycopy(sendPortByte, 0, outputPacket, 0, INT_BYTE_LENGTH);
		System.arraycopy(destPortByte, 0, outputPacket, 4, INT_BYTE_LENGTH);
		System.arraycopy(dataOffset, 0, outputPacket, DATA_OFFSET, INT_BYTE_LENGTH);
		System.arraycopy(checksumByte, 0, outputPacket, CHECKSUM_INDEX, INT_BYTE_LENGTH);
		System.arraycopy(payload, 0, outputPacket, HEADER_LENGTH, payload.length);
	}
	
	public void extractPacket(byte[] inputPacket) {
		this.inputPacket = inputPacket;
		payload = new byte[inputPacket.length - HEADER_LENGTH];
		
		byte[] sendPortByte = new byte[INT_BYTE_LENGTH];
		byte[] destPortByte = new byte[INT_BYTE_LENGTH];
		byte[] dataOffsetByte = new byte[INT_BYTE_LENGTH];
		byte[] checksumByte = new byte[INT_BYTE_LENGTH];
		
		System.arraycopy(inputPacket, 0, sendPortByte, 0, INT_BYTE_LENGTH);
		System.arraycopy(inputPacket, 4, destPortByte, 0, INT_BYTE_LENGTH);
		System.arraycopy(inputPacket, DATA_OFFSET, dataOffsetByte, 0, INT_BYTE_LENGTH);
		System.arraycopy(inputPacket, CHECKSUM_INDEX, checksumByte, 0, INT_BYTE_LENGTH);
		System.arraycopy(inputPacket, HEADER_LENGTH, payload, 0, payload.length);
		
		sourcePort = ByteBuffer.wrap(sendPortByte).getInt();
		destPort = ByteBuffer.wrap(destPortByte).getInt();
		dataOffset = ByteBuffer.wrap(dataOffsetByte).getInt();
		checksum = ByteBuffer.wrap(checksumByte).getInt();
	}
	
	public int computeChecksum(int sourcePort, int destPort, int dataOffset, int packetSize) {
		int checksum = sourcePort + destPort + dataOffset + packetSize;
		checksum = ~checksum;
		
		return checksum;
	}
	
	public boolean verifyChecksum() {
		int calChecksum = computeChecksum(sourcePort, destPort, dataOffset, payload.length);
		return calChecksum == checksum;
	}
	
	public byte[] getOutputPacket() {
		return outputPacket;
	}
	
	public void setOutputPacket(byte[] outputPacket) {
		this.outputPacket = outputPacket;
	}
	
	public byte[] getInputPacket() {
		return inputPacket;
	}
	
	public void setInputPacket(byte[] inputPacket) {
		this.inputPacket = inputPacket;
	}
	
	public byte[] getPayload() {
		return payload;
	}
	
	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	public int getSourcePort() {
		return sourcePort;
	}

	public void setSourcePort(int sourcePort) {
		this.sourcePort = sourcePort;
	}

	public int getDestPort() {
		return destPort;
	}

	public void setDestPort(int destPort) {
		this.destPort = destPort;
	}

	public int getDataOffset() {
		return dataOffset;
	}

	public void setDataOffset(int dataOffset) {
		this.dataOffset = dataOffset;
	}

	public int getChecksum() {
		return checksum;
	}

	public void setChecksum(int checksum) {
		this.checksum = checksum;
	}
}
