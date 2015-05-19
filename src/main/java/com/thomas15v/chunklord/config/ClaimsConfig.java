package com.thomas15v.chunklord.config;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.base.Optional;
import com.thomas15v.chunklord.protection.Claim;
import ninja.leaping.configurate.objectmapping.ObjectMapper;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.io.File;
import java.io.IOException;

public class ClaimsConfig extends GenericConfig {

    private ObjectMapper<Claim> mapper;

    public ClaimsConfig(File configFile) throws IOException {
        super(configFile, 1);
        try {
            mapper = ObjectMapper.forClass(Claim.class);
        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
    }

    public void setClaim(Claim claim){
        try {
            mapper.bind(claim).serialize(getRoot().getNode(claim.getLocation().getX() + "#" + claim.getLocation().getZ()));
            save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Claim> getClaim(int X, int Z){
        return getClaim(X + "#" + Z, new Vector3i(X,0,Z));
    }

    private Optional<Claim> getClaim(String key, Vector3i vector3i){
        if (getRoot().getNode(key).getValue() != null)
            try {
                Claim claim = mapper.bind(new Claim(vector3i)).populate(getRoot().getNode(key));
                return Optional.fromNullable(claim);
            } catch (ObjectMappingException e) {
                e.printStackTrace();
            }
        return Optional.absent();
    }

    public void removeClaim(int X, int Z){
        getRoot().getNode(X + "#" + Z).setValue(null);
        try {
            save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
