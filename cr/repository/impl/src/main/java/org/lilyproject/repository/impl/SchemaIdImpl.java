package org.lilyproject.repository.impl;

import java.util.Arrays;
import java.util.UUID;

import org.lilyproject.bytes.api.DataInput;
import org.lilyproject.bytes.api.DataOutput;
import org.lilyproject.bytes.impl.DataInputImpl;
import org.lilyproject.bytes.impl.DataOutputImpl;
import org.lilyproject.repository.api.SchemaId;

public class SchemaIdImpl implements SchemaId {
    
    private UUID uuid;
    private byte[] bytes;
    private String string;

    public SchemaIdImpl(UUID uuid) {
        this.uuid = uuid;
        this.bytes = idToBytes(uuid);
    }
    
    public SchemaIdImpl(byte[] id) {
        this.bytes = id;
    }
    
    public SchemaIdImpl(String id) {
        this.string = id;
        this.uuid = UUID.fromString(id);
        this.bytes = idToBytes(uuid);
    }
    
    public byte[] getBytes() {
        return bytes;
    }
    
    public String toString() {
        if (string == null) {
            if (uuid == null) {
                DataInput dataInput = new DataInputImpl(bytes);
                this.uuid = new UUID(dataInput.readLong(), dataInput.readLong());
            }
            this.string = uuid.toString();
        }
        return string;
    }
    
    private byte[] idToBytes(UUID uuid) {
        DataOutput dataOutput = new DataOutputImpl(16);
        dataOutput.writeLong(uuid.getMostSignificantBits());
        dataOutput.writeLong(uuid.getLeastSignificantBits());
        return dataOutput.toByteArray();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(bytes);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SchemaIdImpl other = (SchemaIdImpl) obj;
        if (!Arrays.equals(bytes, other.bytes))
            return false;
        return true;
    }
    
    
}