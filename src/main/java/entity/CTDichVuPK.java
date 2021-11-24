package entity;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class CTDichVuPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6365512131159703442L;
	private String dichVu;
    private String hoaDon;

    public CTDichVuPK() {
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dichVu == null) ? 0 : dichVu.hashCode());
        result = prime * result + ((hoaDon == null) ? 0 : hoaDon.hashCode());
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
        CTDichVuPK other = (CTDichVuPK) obj;
        if (dichVu == null) {
            if (other.dichVu != null)
                return false;
        } else if (!dichVu.equals(other.dichVu))
            return false;
        if (hoaDon == null) {
            if (other.hoaDon != null)
                return false;
        } else if (!hoaDon.equals(other.hoaDon))
            return false;
        return true;
    }

}
