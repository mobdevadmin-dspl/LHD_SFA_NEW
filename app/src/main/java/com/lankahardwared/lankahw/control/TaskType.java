package com.lankahardwared.lankahw.control;

public enum TaskType

{
    DATABASENAME(1),
    FITEMLOC(2),
    FFREESLAB(3),
    FFREEHED(4),
    FFREEDET(5),
    FFREEDEB(6),
    FITENRDET(7),
    FITENRHED(8),
    FITEDEBDET(9),
    FITEMPRI(10),
    FITEMS(11),
    FDEBTOR(12),
    FCONTROL(13),
    FCOMPANYSETTING(14),
    FAREA(15),
    FLOCATIONS(16),
    FCOMPANYBRANCH(17),
    FSALREP(18),
    FREASON(19),
    FROUTE(20),
    FBANK(21),
    FDDBNOTE(22),
    FEXPENSE(23),
    FTOWN(24),
    FMERCH(25),
    FROUTEDET(26),
    FTRGCAPUL(27),
    FTYPE(28),
    FSUBBRAND(29),
    FGROUP(30),
    FSKU(31),
    FBRAND(32),
    FDEALER(33),
    FDISCDEB(34),
    FDISCDET(35),
    FDISCSLAB(36),
    FDISCHED(37),
    FFREEITEM(38),
    FFREEMSLAB(39),
    FDISCVHED(40),
    FDISCVDET(41),
    FDISCVDEB(42),
    FINVHEDL3(43),
    FINVDETL3(44),
    FDSCHHED(45),
    FDSCHDET(46),
    FSIZECOMB(47),
    FSIZEIN(48),
    FCRCOMB(49),
    FTERMS(50),
    FTAX(51),
    FTAXHED(52),
    FTAXDET(53),
    FTOURHED(54),
    FDEBITEMPRI(55),
    FSTKIN(56),
    FBRANDTARGET(57),
    FTARGETDET(58),
    FACHIEVEMENT(59),
    UPLOADLOADING(60),
    UPLOADTOUR(61),
    UPLOADVANSALES(62),
    UPLOAD_NONPROD(63),
    UPLOADSPRESALE(64),
    FDISTRICT(65),
    UPLOADNEWCUSTOMER(66),
    FITTOURDISC(67),
    UPLOADSALESRETURN(68),
    FDEBTAX(69),
    FAPPRORDHED(70),
    INVOICESALETM(71),
    INVOICESALEPM(72),
    UPLOADDEPOSIT(73),
    FOTHERTRANS(74),
    UPLOADDEBTORGPS(75),
    UPLOADRECEIPT(76),
    UPLOAD_EXPENSE(77);


    int value;

    private TaskType(int value) {
        this.value = value;
    }
}